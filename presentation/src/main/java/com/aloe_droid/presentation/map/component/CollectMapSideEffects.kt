package com.aloe_droid.presentation.map.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import com.aloe_droid.presentation.base.view.BaseSnackBarVisuals
import com.aloe_droid.presentation.base.view.CollectSideEffects
import com.aloe_droid.presentation.map.contract.MapEffect
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Composable
internal fun CollectMapSideEffects(
    sideEffect: Flow<MapEffect>,
    storeListState: LazyListState,
    showSnackMessage: (SnackbarVisuals) -> Unit,
    navigateToStore: (UUID) -> Unit,
) {
    CollectSideEffects(effectFlow = sideEffect) { sideEffect: MapEffect ->
        when (sideEffect) {
            is MapEffect.ShowErrorMessage -> {
                val snackBarVisuals = BaseSnackBarVisuals(message = sideEffect.message)
                showSnackMessage(snackBarVisuals)
            }

            is MapEffect.NavigateStore -> {
                navigateToStore(sideEffect.id)
            }

            MapEffect.ScrollToFirstPosition -> {
                storeListState.animateScrollToItem(0)
            }
        }
    }
}
