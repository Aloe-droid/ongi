package com.aloe_droid.presentation.store.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.Menu
import java.util.UUID

@Stable
data class MenuData(
    val id: UUID,
    val name: String,
    val price: String
) {
    companion object {
        fun List<Menu>.toMenuDataList(): List<MenuData> = map { it.toMenuData() }

        fun Menu.toMenuData() = MenuData(
            id = id,
            name = name,
            price = price
        )
    }
}
