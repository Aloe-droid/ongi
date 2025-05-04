package com.aloe_droid.domain.usecase

import androidx.paging.PagingData
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<PagingData<SearchHistory>> = searchHistoryRepository
        .getAllHistoriesPaged()

}
