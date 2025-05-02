package com.aloe_droid.presentation.store.contract

import com.aloe_droid.presentation.base.view.UiContract
import kotlinx.serialization.Serializable

@Serializable
data class Store(val id: String) : UiContract.Route
