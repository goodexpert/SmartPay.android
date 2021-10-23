package org.goodexpert.apps.smartpay.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler

@Composable
fun AppLocalProvider(
    backPressHandler: BackPressHandler,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBackPressHandler provides backPressHandler,
        content = content
    )
}