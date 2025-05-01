package com.aloe_droid.data.datasource.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.dto.store.StorePage
import com.aloe_droid.domain.entity.StoreQuery
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StorePagingSource @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val storeQuery: StoreQuery
) : PagingSource<Int, StoreDTO>() {

    override fun getRefreshKey(state: PagingState<Int, StoreDTO>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoreDTO> {
        val page = params.key ?: START_PAGE_INDEX
        val query: StoreQuery = storeQuery.copy(page = page)

        return try {
            val storePage: StorePage = storeDataSource.getStoreList(storeQuery = query).first()
            val nextKey: Int? = if (storePage.hasNext) page + 1 else null
            val prevKey: Int? = if (storePage.hasPrev) page - 1 else null
            LoadResult.Page(data = storePage.stores, prevKey = prevKey, nextKey = nextKey)
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable = throwable)
        }
    }

    companion object {
        private const val START_PAGE_INDEX = 0
    }
}
