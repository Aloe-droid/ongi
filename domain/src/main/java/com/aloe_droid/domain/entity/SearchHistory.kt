package com.aloe_droid.domain.entity

data class SearchHistory(
    val id: Long = 0,
    val keyword: String,
    val timestamp: Long
)
