package com.aloe_droid.presentation.base.theme

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.filtered_store.data.StoreDistanceRange
import java.util.Date
import java.util.Locale

fun Double.toDistance(): String = String.format(Locale.getDefault(), "%.1fkm", this)

fun Int.toFavorite(): String = when {
    this < 10 -> this.toString()
    this < 100 -> "10+"
    this < 1_000 -> "100+"
    else -> "1000+"
}

fun Long.toTimeStamp(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("MM.dd", Locale.getDefault())
    return formatter.format(date)
}

@Composable
fun StoreDistanceRange.toDistanceString(): String {
    return if (this == StoreDistanceRange.NONE) {
        stringResource(id = R.string.max_range)
    } else {
        "${this.getKm()}km"
    }
}

@Composable
fun StoreDistanceRange.toSelectDistanceString(): String {
    return if (this == StoreDistanceRange.NONE) {
        this.toDistanceString()
    } else {
        "${stringResource(id = R.string.select_range)} ${this.toDistanceString()}"
    }
}

