package com.aloe_droid.data.repository.mapper

import com.aloe_droid.data.datasource.local.entity.SearchHistoryEntity
import com.aloe_droid.domain.entity.SearchHistory

object SearchHistoryMapper {

    fun SearchHistoryEntity.toSearchHistory() = SearchHistory(
        id = id,
        keyword = keyword,
        timestamp = timestamp,
    )

    fun SearchHistory.toSearchHistoryEntity() = SearchHistoryEntity(keyword = keyword)
}
