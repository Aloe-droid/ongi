package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {

    suspend operator fun invoke(historyId: Long) {
        searchHistoryRepository.deleteById(id = historyId)
    }

    suspend operator fun invoke() {
        searchHistoryRepository.deleteAll()
    }

}
