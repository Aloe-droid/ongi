package com.aloe_droid.data.datasource.local.util

import java.math.BigDecimal
import java.math.RoundingMode

object Location {
    fun Double.toScale(scale: Int = 3): Double {
        return BigDecimal(this).setScale(scale, RoundingMode.DOWN).toDouble()
    }
}
