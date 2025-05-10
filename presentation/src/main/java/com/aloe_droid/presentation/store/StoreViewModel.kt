package com.aloe_droid.presentation.store

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aloe_droid.domain.entity.StoreEntity
import com.aloe_droid.domain.usecase.GetStoreInfoUseCase
import com.aloe_droid.domain.usecase.ToggleStoreLikeUseCase
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.store.contract.Store
import com.aloe_droid.presentation.store.contract.StoreEffect
import com.aloe_droid.presentation.store.contract.StoreEvent
import com.aloe_droid.presentation.store.contract.StoreUiState
import com.aloe_droid.presentation.store.data.AddressData
import com.aloe_droid.presentation.store.data.StoreData
import com.aloe_droid.presentation.store.data.StoreData.Companion.toStoreData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStoreInfoUseCase: GetStoreInfoUseCase,
    private val toggleStoreLikeUseCase: ToggleStoreLikeUseCase
) : BaseViewModel<StoreUiState, StoreEvent, StoreEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): StoreUiState {
        val store: Store = savedStateHandle.toRoute()
        return StoreUiState(id = UUID.fromString(store.id))
    }

    override fun handleEvent(event: StoreEvent) {
        when (event) {
            StoreEvent.LoadEvent -> handleLoad()
            is StoreEvent.CantFindStoreEvent -> handleCantFindStore(event.message)
            StoreEvent.ToggleFavorite -> handleToggleFavorite()
            is StoreEvent.CallEvent -> handleCall(event.phone)
            is StoreEvent.MapEvent -> handleMap(event.address)
        }
    }

    private fun handleLoad() = viewModelScope.safeLaunch {
        val storeId: UUID = currentState.id
        getStoreInfoUseCase(storeId = storeId).safeCollect { storeEntity: StoreEntity ->
            updateState { uiState: StoreUiState ->
                uiState.copy(isInitialState = false, store = storeEntity.toStoreData())
            }
        }
    }

    private fun handleToggleFavorite() = viewModelScope.safeLaunch {
        val storeId: UUID = currentState.id
        toggleStoreLikeUseCase(storeId = storeId).safeCollect { isLike: Boolean ->
            toggleFavorite(isLike = isLike)
        }
    }

    private fun handleCall(phone: String) {
        val effect = StoreEffect.MoveToCall(phone = phone)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleMap(address: AddressData) {
        val effect = StoreEffect.MoveToMap(address = address)
        sendSideEffect(uiEffect = effect)
    }

    private fun handleCantFindStore(message: String) {
        val effect: StoreEffect = StoreEffect.PopUpWithMessage(message = message)
        sendSideEffect(effect)
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        updateState { state: StoreUiState ->
            state.copy(isInitialState = false)
        }

        throwable.message?.let { message: String ->
            showErrorMessage(message = message)
        }
    }

    private fun showErrorMessage(message: String) {
        val effect: StoreEffect = StoreEffect.ShowErrorMessage(message = message)
        sendSideEffect(uiEffect = effect)
    }

    private fun toggleFavorite(isLike: Boolean) {
        updateState { uiState: StoreUiState ->
            val prevStore = uiState.store ?: return@updateState uiState
            val favoriteCount = with(prevStore.favoriteCount) {
                if (isLike) plus(1) else minus(1)
            }
            val store: StoreData = prevStore.copy(
                favoriteCount = favoriteCount,
                isLikeStore = isLike
            )
            uiState.copy(store = store)
        }
    }
}
