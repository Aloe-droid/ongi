package com.aloe_droid.presentation.store.data

enum class StoreDistanceRange(val meters: Float) {
    M_5(meters = 500f),
    K_1(meters = 1000f),
    K_3(meters = 3000f),
    K_5(meters = 5000f),
    K_10(meters = 10000f),
    K_15(meters = 15000f),
    NONE(meters = -1f)
}
