package com.aloe_droid.presentation.search.contract.route

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aloe_droid.presentation.base.UiContract
import com.aloe_droid.presentation.search.SearchScreen
import com.aloe_droid.presentation.search.SearchViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Search : UiContract.Route


fun NavGraphBuilder.searchScreen() = composable<Search> {
    val searchViewModel: SearchViewModel = hiltViewModel()
    SearchScreen()
}
