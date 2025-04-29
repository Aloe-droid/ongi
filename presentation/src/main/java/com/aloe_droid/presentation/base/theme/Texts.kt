package com.aloe_droid.presentation.base.theme

import java.util.Locale

fun Double.toDistance(): String = String.format(Locale.getDefault(), "%.1fkm", this)

fun Int.toFavorite(): String = when {
    this < 10 -> this.toString()
    this < 100 -> "10+"
    this < 1_000 -> "100+"
    else -> "1000+"
}
