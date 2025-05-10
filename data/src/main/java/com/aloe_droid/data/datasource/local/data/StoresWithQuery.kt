package com.aloe_droid.data.datasource.local.data

import com.aloe_droid.data.datasource.local.entity.StoreEntity

data class StoresWithQuery(
    val storeList: List<StoreEntity>,
    val queryId: String
)
