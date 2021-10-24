package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview
import org.goodexpert.apps.smartpay.R

private fun isValidNumber(text: String): Boolean {
    val regex = """[+-]?([0-9]+([.][0-9]*)?|[.]\d{2})""".toRegex()
    return text.isEmpty() || regex.matches(text)
}

@Composable
fun EnterAmountDialog(
    onConfirm: (Number) -> Unit,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var value by remember { mutableStateOf("") }
    val onDone: () -> Unit = {
        coroutineScope.launch {
            onConfirm(value.toDoubleOrNull() ?: 0.0)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("alertDialog"),
        confirmButton = {
            TextButton(onClick = onDone) {
                Text(
                    text = stringResource(id = R.string.btn_confirm),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.button
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.button
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.title_enter_an_amount),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.subtitle1
            )
        },
        text = {
            TextField(
                value = value,
                onValueChange = {
                    if (isValidNumber(it)) {
                        value = it
                    }
                },
                modifier = Modifier
                    .testTag("textField")
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                placeholder = {
                    Text(
                        text = "0.00",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone()
                    }
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.onPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEnterAmountDialog() {
    ThemedPreview {
        EnterAmountDialog(
            onConfirm = { },
            onDismiss = { }
        )
    }
}