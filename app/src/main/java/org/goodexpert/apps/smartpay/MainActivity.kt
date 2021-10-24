package org.goodexpert.apps.smartpay

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.zsoltk.compose.backpress.BackPressHandler
import dagger.hilt.android.AndroidEntryPoint
import org.goodexpert.apps.smartpay.ui.SmartPayApp
import org.goodexpert.apps.smartpay.ui.theme.AppLocalProvider
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var backPressHandler: BackPressHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            AppLocalProvider(
                backPressHandler = backPressHandler
            ) {
                SmartPayApp()
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}