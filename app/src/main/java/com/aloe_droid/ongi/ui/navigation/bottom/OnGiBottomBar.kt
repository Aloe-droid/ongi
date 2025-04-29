package com.aloe_droid.ongi.ui.navigation.bottom

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.aloe_droid.ongi.ui.theme.SelectColor
import com.aloe_droid.ongi.ui.theme.UnSelectColor
import com.aloe_droid.presentation.base.view.UiContract.Route
import com.aloe_droid.presentation.base.theme.ZeroDp

@Composable
fun OnGiBottomBar(
    bottomRouteList: List<BottomRoute>,
    backStackEntry: NavBackStackEntry?,
    selectRoute: (Route) -> Unit
) {
    NavigationBar(
        windowInsets = WindowInsets(bottom = ZeroDp)
    ) {
        bottomRouteList.forEach { bottomRoute: BottomRoute ->
            with(bottomRoute) {
                val isSelected: Boolean = backStackEntry?.let {
                    backStackEntry.destination.hierarchy.any {
                        it.hasRoute(bottomRoute.route::class)
                    }
                } == true
                val res: Int = if (isSelected) selectedIconRes else unSelectedIconRes
                val color: Color = if(isSelected) SelectColor else UnSelectColor

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = name,
                            tint = color
                        )
                    },
                    label = { Text(text = name, color = color) },
                    selected = isSelected,
                    onClick = { selectRoute(route) }
                )
            }
        }

    }
}
