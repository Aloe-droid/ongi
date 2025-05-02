package com.aloe_droid.presentation.store.data

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.core.net.toUri

@Stable
data class AddressData(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
) {
    fun toUri() : Uri {
        return "geo:0,0?q=${latitude},${longitude}(${name})".toUri()
    }
}
