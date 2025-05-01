package com.aloe_droid.presentation.filtered_store.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.filtered_store.data.StoreFilter
import kotlinx.serialization.Serializable

@Serializable
data class FilteredStore(
    val storeFilter: StoreFilter,
) : UiContract.Route
