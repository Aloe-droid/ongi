package com.aloe_droid.ongi.ui.navigation.bottom

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.toRoute
import com.aloe_droid.presentation.base.theme.BottomBarHeight
import com.aloe_droid.presentation.base.theme.ZeroDp
import com.aloe_droid.presentation.base.view.UiContract.Route
import com.aloe_droid.presentation.search.contract.Search
import kotlin.reflect.KClass

@Composable
fun OnGiBottomBar(
    bottomRouteList: List<BottomRoute>,
    backStackEntry: NavBackStackEntry?,
    selectRoute: (Route) -> Unit
) {
    if (backStackEntry.isBottomSearch() || backStackEntry.isNowBottomRoute()) {
        BottomBar(
            bottomRouteList = bottomRouteList,
            backStackEntry = backStackEntry,
            selectRoute = selectRoute
        )
    }
}

@Composable
private fun BottomBar(
    bottomRouteList: List<BottomRoute>,
    backStackEntry: NavBackStackEntry?,
    selectRoute: (Route) -> Unit,
    selectedColor: Color = MaterialTheme.colorScheme.onSurface,
    unSelectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
) {
    NavigationBar(
        modifier = Modifier.height(BottomBarHeight),
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
                val color: Color = if (isSelected) selectedColor else unSelectedColor

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

private fun NavBackStackEntry?.isBottomSearch(): Boolean {
    if (this == null) return false
    val search: Search? = runCatching {
        toRoute<Search>()
    }.getOrNull()

    return !(search == null || !search.isFromBottomNavigate)
}

private fun NavBackStackEntry?.isNowBottomRoute(): Boolean =
    this?.destination?.containsBottomRoute() == true

private fun NavDestination.containsBottomRoute(): Boolean {
    val bottomList: List<KClass<*>> = BottomRoute.DefaultBottomList.map { it.route::class }
    return containsRoute(bottomList)
}

private fun NavDestination.containsRoute(targets: List<KClass<*>>): Boolean {
    return targets.filterNot { it == Search::class }.any { target: KClass<*> ->
        hasRoute(target)
    }
}
