package com.aloe_droid.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aloe_droid.presentation.base.theme.DefaultPadding
import com.aloe_droid.presentation.base.theme.LargePadding
import com.aloe_droid.presentation.base.theme.SemiLargePadding
import com.aloe_droid.presentation.setting.component.SettingButtons
import com.aloe_droid.presentation.setting.component.StoreSyncInfo
import kotlinx.datetime.Instant

@Composable
fun SettingScreen(
    storeCount: Long,
    syncTime: Instant,
    onClickFavoriteStore: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickInQueryToDeveloper: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SemiLargePadding)
    ) {
        StoreSyncInfo(
            modifier = Modifier.padding(top = DefaultPadding, bottom = LargePadding),
            storeCount = storeCount,
            syncTime = syncTime
        )

        SettingButtons(
            onClickFavoriteStore = onClickFavoriteStore,
            onClickPrivacyPolicy = onClickPrivacyPolicy,
            onClickInQueryToDeveloper = onClickInQueryToDeveloper
        )
    }
}
