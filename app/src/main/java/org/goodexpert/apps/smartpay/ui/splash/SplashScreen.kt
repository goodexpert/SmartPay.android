package org.goodexpert.apps.smartpay.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun SplashScreen(
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1000L)
        onDismiss()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    ThemedPreview {
        SplashScreen(
            onDismiss = { }
        )
    }
}