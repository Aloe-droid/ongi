package com.aloe_droid.data.repository.impl

import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.dto.banner.BannerDTO
import com.aloe_droid.data.datasource.network.source.BannerDataSource
import com.aloe_droid.data.repository.mapper.BannerMapper.toBannerList
import com.aloe_droid.domain.entity.Banner
import com.aloe_droid.domain.repository.BannerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannerDataSource: BannerDataSource,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : BannerRepository {

    private val defaultBanner = listOf(Banner(url = GOOD_PRICE_URL, imageUrl = ""))
    override fun getBannerList(): Flow<List<Banner>> {
        return bannerDataSource.getBannerList()
            .map { list: List<BannerDTO> -> list.toBannerList() }
            .map { list: List<Banner> -> if (list.isEmpty()) defaultBanner else list }
            .flowOn(ioDispatcher)
    }

    companion object {
        private const val GOOD_PRICE_URL = "https://www.goodprice.go.kr/"
    }
}
