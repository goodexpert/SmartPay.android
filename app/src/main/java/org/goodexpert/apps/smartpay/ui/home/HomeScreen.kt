package org.goodexpert.apps.smartpay.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.ui.theme.buttonLarge
import org.goodexpert.apps.smartpay.ui.theme.paddingNone
import org.goodexpert.apps.smartpay.ui.theme.paddingSmall
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun HomeScreen(
    onClick: () -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonLarge)
                .padding(paddingSmall),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            Text(
                text = stringResource(id = R.string.btn_purchase_with_new_card)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    ThemedPreview {
        HomeScreen(
            onClick = { },
            contentPadding = paddingNone
        )
    }
}