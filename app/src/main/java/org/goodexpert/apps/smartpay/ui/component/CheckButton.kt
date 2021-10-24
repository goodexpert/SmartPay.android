package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.ui.theme.Shapes
import org.goodexpert.apps.smartpay.ui.theme.buttonSmall
import org.goodexpert.apps.smartpay.ui.theme.checkButtonMinWidth
import org.goodexpert.apps.smartpay.ui.theme.paddingTiny
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun CheckButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.textButtonColors()
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .testTag("checkButton")
            .defaultMinSize(minWidth = checkButtonMinWidth)
            .height(buttonSmall),
        shape = shape,
        colors = colors,
        contentPadding = PaddingValues(paddingTiny)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isChecked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null
                )
            }

            Text(
                text = text,
                modifier = Modifier.padding(horizontal = paddingTiny),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckButton() {
    ThemedPreview {
        CheckButton(
            onClick = { },
            text = "Yes",
            isChecked = true,
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
        )
    }
}