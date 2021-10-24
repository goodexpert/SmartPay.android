package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.goodexpert.apps.smartpay.ui.theme.defaultElevation
import org.goodexpert.apps.smartpay.ui.theme.paddingSmall
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String? = null,
    placeholder: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    var displayText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        if (label != null) {
            Text(
                text = label,
                modifier = Modifier
                    .testTag("label")
                    .padding(paddingSmall),
                style = MaterialTheme.typography.caption
            )
        }

        Card(
            modifier = Modifier.testTag("container"),
            elevation = defaultElevation
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .semantics { contentDescription = displayText }
                    .testTag("textField")
                    .fillMaxWidth(),
                textStyle = textStyle.copy(textAlign = TextAlign.Center),
                placeholder = {
                    Text(
                        text = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        style = textStyle.copy(textAlign = TextAlign.Center)
                    )
                },
                isError = isError,
                visualTransformation = {
                    val transformedText = visualTransformation.filter(it)
                    displayText = transformedText.text.toString()
                    transformedText
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

fun cardNumberTransformedText(text: AnnotatedString): TransformedText {
    // Making XXXX-****-****-XXXX string.
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""
    for (i in trimmed.indices) {
        if (i > 3 && i <= 11) {
            out += "*"
        } else {
            out += trimmed[i]
        }
        if (i % 4 == 3 && i != 15) out += "-"
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }
    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun monthYearTransformedText(text: AnnotatedString): TransformedText {
    // Making MM/YY string.
    val trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 1) out += "/"
    }

    val monthYearOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 5) return offset + 1
            return 5
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 5) return offset - 1
            return 4
        }
    }
    return TransformedText(AnnotatedString(out), monthYearOffsetTranslator)
}

@Preview(showBackground = true)
@Composable
fun PreviewTextField() {
    ThemedPreview {
        TextField(
            value = "5456789012345670",
            onValueChange = { },
            modifier = Modifier.padding(paddingSmall),
            label = "Enter CARD PAN",
            placeholder = "XXXX-XXXX-XXXX-XXXX",
            visualTransformation = {
                cardNumberTransformedText(it)
            }
        )
    }
}