package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_histories")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val keyword: String,
    val timestamp: Long = System.currentTimeMillis()
)
