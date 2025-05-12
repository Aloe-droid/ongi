package com.aloe_droid.ongi.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aloe_droid.ongi.ui.theme.OnGiTheme
import com.aloe_droid.presentation.splash.SplashViewModel
import com.aloe_droid.presentation.splash.contract.effect.SplashEffect
import com.aloe_droid.presentation.splash.contract.event.SplashEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
        initCollect()
        if (splashViewModel.currentState.isInitState) {
            val event: SplashEvent = SplashEvent.CheckAuth
            splashViewModel.sendEvent(event = event)
        }

        enableEdgeToEdge()
        setContent {
            OnGiTheme(dynamicColor = false) {
                OnGiApp()
            }
        }
    }

    private fun initView() {
        val splashScreen: SplashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { splashViewModel.currentState.isLoading }
    }

    private fun initCollect() = lifecycleScope.launch {
        collectUiState()
        collectSideEffect()
    }

    private fun CoroutineScope.collectUiState() = launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            splashViewModel.uiState.collect { }
        }
    }

    private fun CoroutineScope.collectSideEffect() = launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            splashViewModel.uiEffect.filterIsInstance<SplashEffect.FinishSplashScreen>()
                .collect { effect: SplashEffect.FinishSplashScreen ->
                    val message: String? = effect.throwable.message
                    val context: Context = this@MainActivity
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

}
