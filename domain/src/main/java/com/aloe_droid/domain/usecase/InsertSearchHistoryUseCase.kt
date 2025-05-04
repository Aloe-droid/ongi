package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(keyword: String) {
        val searchHistory = SearchHistory(keyword = keyword, timestamp = System.currentTimeMillis())
        searchHistoryRepository.insert(history = searchHistory)
    }
}
