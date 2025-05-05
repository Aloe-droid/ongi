package com.aloe_droid.presentation.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aloe_droid.domain.entity.StoreSyncEntity
import com.aloe_droid.domain.usecase.GetStoreSyncInfoUseCase
import com.aloe_droid.presentation.base.view.BaseViewModel
import com.aloe_droid.presentation.setting.contract.SettingEffect
import com.aloe_droid.presentation.setting.contract.SettingEvent
import com.aloe_droid.presentation.setting.contract.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStoreSyncInfoUseCase: GetStoreSyncInfoUseCase,
) : BaseViewModel<SettingUiState, SettingEvent, SettingEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SettingUiState {
        return SettingUiState()
    }

    override fun handleEvent(event: SettingEvent) = when (event) {
        SettingEvent.LoadEvent -> handleLoad()
        SettingEvent.ClickFavoriteStore -> handleNavigateToFilteredStore()
        SettingEvent.ClickPrivacyPolicy -> handleMoveToPrivacyPolicy()
        SettingEvent.ClickInquiryToDeveloper -> handleMoveToInQueryToDeveloper()
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        updateState { state: SettingUiState ->
            state.copy(isInitialState = false)
        }

        throwable.message?.let { message: String ->
            showErrorMessage(message = message)
        }
    }

    private fun handleLoad() = viewModelScope.safeLaunch {
        getStoreSyncInfoUseCase().safeCollect { storeSyncEntity: StoreSyncEntity ->
            updateState { uiState: SettingUiState ->
                uiState.copy(
                    isInitialState = false,
                    storeCount = storeSyncEntity.storeCount,
                    syncTime = storeSyncEntity.syncTime
                )
            }
        }
    }

    private fun handleNavigateToFilteredStore() {
        val effect = SettingEffect.NavigateToFilteredStore
        sendSideEffect(uiEffect = effect)
    }

    private fun handleMoveToPrivacyPolicy() {
        val effect = SettingEffect.MoveToPrivacyPolicy
        sendSideEffect(uiEffect = effect)
    }

    private fun handleMoveToInQueryToDeveloper() {
        val effect = SettingEffect.MoveToInQueryToDeveloper
        sendSideEffect(uiEffect = effect)
    }

    private fun showErrorMessage(message: String) {
        val effect: SettingEffect = SettingEffect.ShowErrorMessage(message = message)
        sendSideEffect(uiEffect = effect)
    }
}
