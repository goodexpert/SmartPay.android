package org.goodexpert.apps.smartpay.ui.util

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.github.zsoltk.compose.backpress.BackPressHandler
import org.goodexpert.apps.smartpay.ui.theme.AppLocalProvider
import org.goodexpert.apps.smartpay.ui.theme.SmartPayTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    AppLocalProvider(
        backPressHandler = BackPressHandler()
    ) {
        SmartPayTheme(darkTheme = darkTheme) {
            Surface() {
                content()
            }
        }
    }
}