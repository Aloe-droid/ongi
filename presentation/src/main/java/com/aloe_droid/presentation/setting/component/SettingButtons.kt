package com.aloe_droid.presentation.setting.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.base.component.IconText
import com.aloe_droid.presentation.base.theme.SemiLargePadding

@Composable
fun ColumnScope.SettingButtons(
    onClickFavoriteStore: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickInQueryToDeveloper: () -> Unit,
) {

    IconText(
        text = stringResource(id = R.string.favorited_store),
        iconRes = R.drawable.star,
        shape = RectangleShape,
        textPadding = SemiLargePadding,
        onClick = onClickFavoriteStore
    )

    IconText(
        text = stringResource(id = R.string.privacy_policy),
        iconRes = R.drawable.sticky_note,
        shape = RectangleShape,
        textPadding = SemiLargePadding,
        onClick = onClickPrivacyPolicy
    )

    IconText(
        text = stringResource(id = R.string.inquiry),
        iconRes = R.drawable.email,
        shape = RectangleShape,
        textPadding = SemiLargePadding,
        onClick = onClickInQueryToDeveloper
    )
}
