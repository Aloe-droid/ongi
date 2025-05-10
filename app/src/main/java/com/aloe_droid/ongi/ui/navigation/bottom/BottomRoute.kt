package com.aloe_droid.ongi.ui.navigation.bottom

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.aloe_droid.ongi.R
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.contract.Home
import com.aloe_droid.presentation.map.contract.Map
import com.aloe_droid.presentation.search.contract.Search
import com.aloe_droid.presentation.setting.contract.Setting

@Stable
data class BottomRoute(
    val name: String,
    val route: UiContract.Route,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unSelectedIconRes: Int
) {
    companion object {
        private val HomeRoute = BottomRoute(
            name = "홈",
            route = Home,
            selectedIconRes = R.drawable.grid_view_fill_24px,
            unSelectedIconRes = R.drawable.grid_view_24px,
        )

        private val SearchRoute = BottomRoute(
            name = "검색",
            route = Search(isFromBottomNavigate = true),
            selectedIconRes = R.drawable.search_fill_24px,
            unSelectedIconRes = R.drawable.search_24px
        )

        private val MapRoute = BottomRoute(
            name = "지도",
            route = Map,
            selectedIconRes = R.drawable.map_fill_24px,
            unSelectedIconRes = R.drawable.map_24px
        )

        private val SettingRoute = BottomRoute(
            name = "설정",
            route = Setting,
            selectedIconRes = R.drawable.settings_fill_24px,
            unSelectedIconRes = R.drawable.settings_24px
        )

        val DefaultBottomList: List<BottomRoute> = listOf(
            HomeRoute,
            SearchRoute,
            MapRoute,
            SettingRoute
        )
    }
}
