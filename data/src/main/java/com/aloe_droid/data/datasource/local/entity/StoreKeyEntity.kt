package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity

@Entity(tableName = "store_keys", primaryKeys = ["queryId", "storeId"])
data class StoreKeyEntity(
    val storeId: String,
    val queryId: String, // TODO("추후에 쿼리 별로 DB 캐시 정책)
    val prevKey: Int?,
    val nextKey: Int?
)
