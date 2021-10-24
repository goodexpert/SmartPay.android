package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.goodexpert.apps.smartpay.ui.theme.actionBarElevation
import org.goodexpert.apps.smartpay.ui.theme.actionBarPadding
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun ActionBar(
    onClick: () -> Unit,
    title: String,
    navigationIconId: Int = 0,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    TopAppBar(
        title = {
            Text(
                text = title.uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(actionBarPadding),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        },
        navigationIcon = {
            if (navigationIconId != 0) {
                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        painter = painterResource(id = navigationIconId),
                        contentDescription = null
                    )
                }
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = actionBarElevation
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewActionBar() {
    ThemedPreview {
        ActionBar(
            onClick = { },
            title = "Smart Pay"
        )
    }
}