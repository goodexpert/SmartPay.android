package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.goodexpert.apps.smartpay.ui.theme.defaultElevation
import org.goodexpert.apps.smartpay.ui.theme.paddingLarge
import org.goodexpert.apps.smartpay.ui.theme.paddingSmall
import org.goodexpert.apps.smartpay.ui.theme.progressIndicatorSize
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun ProgressIndicator(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(progressIndicatorSize / 2),
        color = MaterialTheme.colors.surface,
        elevation = defaultElevation
    ) {
        Row(
            modifier = modifier
                .wrapContentWidth()
                .height(progressIndicatorSize)
                .padding(paddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = text,
                modifier = Modifier.padding(horizontal = paddingLarge),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressIndicator() {
    ThemedPreview {
        ProgressIndicator(
            text = "Running..."
        )
    }
}