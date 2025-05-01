package com.aloe_droid.presentation.filtered_store.data

import androidx.navigation.NavType
import com.aloe_droid.presentation.base.view.BaseNavType
import com.aloe_droid.presentation.filtered_store.contract.FilteredStore
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@OptIn(InternalSerializationApi::class)
object StoreFilterNavTypes {
    private val StoreFilterNavType = BaseNavType<StoreFilter>(StoreFilter::class.serializer())
    private val FilteredStoreNavType = BaseNavType<FilteredStore>(FilteredStore::class.serializer())

    val StoreFilterTypeMap: Map<KType, NavType<*>> = mapOf(
        typeOf<StoreFilter>() to StoreFilterNavType,
        typeOf<FilteredStore>() to FilteredStoreNavType,
    )
}
