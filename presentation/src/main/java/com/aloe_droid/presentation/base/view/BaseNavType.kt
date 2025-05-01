package com.aloe_droid.presentation.base.view

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class BaseNavType<T>(
    private val serializer: KSerializer<T>,
    isNullableAllowed: Boolean = false
) : NavType<T>(isNullableAllowed) {

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putString(key, serializeAsValue(value))

    override fun parseValue(value: String): T =
        Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String =
        Json.encodeToString(serializer, value)
}
