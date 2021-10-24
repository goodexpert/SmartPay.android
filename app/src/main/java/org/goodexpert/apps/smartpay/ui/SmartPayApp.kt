package org.goodexpert.apps.smartpay.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.github.zsoltk.compose.router.Router
import kotlinx.coroutines.launch
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.ui.component.ActionBar
import org.goodexpert.apps.smartpay.ui.component.EnterAmountDialog
import org.goodexpert.apps.smartpay.ui.home.HomeScreen
import org.goodexpert.apps.smartpay.ui.splash.SplashScreen
import org.goodexpert.apps.smartpay.ui.theme.SmartPayTheme

@Composable
fun SmartPayApp(
    defaultRouting: Routing = Routing.Splash
) {
    val coroutineScope = rememberCoroutineScope()

    SmartPayTheme {
        Router(defaultRouting = defaultRouting) { backStack ->
            val currentRouting = backStack.last()

            Crossfade(currentRouting) { routing ->
                when (routing) {
                    is Routing.Splash -> {
                        SplashScreen(
                            onDismiss = {
                                coroutineScope.launch {
                                    backStack.replace(Routing.MainContent)
                                }
                            }
                        )
                    }
                    else -> {
                        MainContent()
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    defaultRouting: Routing = Routing.HomeScreen,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val coroutineScope = rememberCoroutineScope()
    var navigationIconId by remember { mutableStateOf(0) }
    var stringId by remember { mutableStateOf(R.string.app_name) }

    Router(defaultRouting = defaultRouting) { backStack ->
        val currentRouting = backStack.last()
        val onDismiss: () -> Unit = {
            coroutineScope.launch {
                backStack.pop()
                navigationIconId = 0
                stringId = R.string.app_name
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ActionBar(
                    onClick = onDismiss,
                    title = stringResource(id = stringId),
                    navigationIconId = navigationIconId
                )
            },
        ) { contentPadding ->

            HomeScreen(
                navigationTo = {
                    backStack.push(it)
                    navigationIconId = R.drawable.ic_arrow_back
                    stringId = R.string.title_moto_trans_type
                },
                contentPadding = contentPadding
            )

            Crossfade(currentRouting) { routing ->
                when (routing) {
                    is Routing.EnterAmount -> {
                        EnterAmountDialog(
                            onConfirm = {
                            },
                            onDismiss = onDismiss
                        )
                    }
                    else -> {
                        // TODO: Not implemented
                    }
                }
            }
        }
    }
}