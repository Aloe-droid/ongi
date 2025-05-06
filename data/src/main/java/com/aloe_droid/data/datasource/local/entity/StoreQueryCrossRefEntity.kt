package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "store_query_cross_ref",
    primaryKeys = ["queryId", "storeId"],
    foreignKeys = [
        ForeignKey(
            entity = StoreEntity::class,
            parentColumns = ["id"],
            childColumns = ["storeId"],
        ),
        ForeignKey(
            entity = StoreQueryEntity::class,
            parentColumns = ["id"],
            childColumns = ["queryId"],
        )
    ],
    indices = [
        Index("storeId"),
        Index("queryId")
    ]
)
data class StoreQueryCrossRefEntity(
    val queryId: String,
    val storeId: String,
    val pageNumber: Int,
    val orderIndex: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
