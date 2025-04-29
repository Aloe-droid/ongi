package com.aloe_droid.data.repository.mapper

import com.aloe_droid.data.datasource.dto.banner.BannerDTO
import com.aloe_droid.domain.entity.Banner

object BannerMapper {

    fun List<BannerDTO>.toBannerList(): List<Banner> = map { it.toBanner() }

    fun BannerDTO.toBanner(): Banner = Banner(
        url = url,
        imageUrl = imageUrl
    )

    fun Banner.toBannerDTO(): BannerDTO = BannerDTO(
        url = url,
        imageUrl = imageUrl
    )
}
