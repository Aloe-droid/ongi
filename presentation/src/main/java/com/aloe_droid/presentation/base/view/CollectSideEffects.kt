package com.aloe_droid.presentation.base.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <S : UiContract.SideEffect> CollectSideEffects(
    effectFlow: Flow<S>,
    onEffect: suspend (S) -> Unit
) = LaunchedEffect(key1 = effectFlow) {
    effectFlow.collect { effect: S ->
        onEffect(effect)
    }
}
