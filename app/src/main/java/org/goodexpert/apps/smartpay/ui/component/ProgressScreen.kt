package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun ProgressScreen(
    onDismiss: () -> Unit,
    onProcess: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    val backPressHandler = LocalBackPressHandler.current

    DisposableEffect(Unit) {
        onDispose {
            backPressHandler.children.removeLast()
        }
    }

    LaunchedEffect(Unit) {
        backPressHandler.children.add {
            onDismiss()
            true
        }
        onProcess()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    ) {
        ProgressIndicator(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressScreen() {
    ThemedPreview {
        ProgressScreen(
            onDismiss = { },
            onProcess = { },
            text = "Running..."
        )
    }
}