package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.ui.theme.buttonNormal
import org.goodexpert.apps.smartpay.ui.theme.defaultElevation
import org.goodexpert.apps.smartpay.ui.theme.paddingSmall
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview
import kotlin.math.min

@Composable
fun <T : Any> CompoBox(
    onValueChange: (Int) -> Unit,
    selected: Int,
    elements: List<T>,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    label: String? = null,
    maxVisibleCount: Int = 3
) {
    val coroutineScope = rememberCoroutineScope()
    val textLabel = elements.get(selected).toString().uppercase()
    val visibleCount = min(elements.count(), maxVisibleCount)
    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .semantics { contentDescription = textLabel }
            .fillMaxWidth()
            .wrapContentHeight()
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

        TextButton(
            onClick = {
                coroutineScope.launch {
                    visible = !visible
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonNormal),
            elevation = ButtonDefaults.elevation(defaultElevation),
            colors = colors
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (label, option) = createRefs()

                Text(
                    text = textLabel,
                    modifier = Modifier
                        .constrainAs(label) {
                            centerTo(parent)
                        },
                    color = MaterialTheme.colors.onSecondary
                )

                Icon(
                    painter = painterResource(id = if (visible) R.drawable.ic_collapse else R.drawable.ic_expand),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(option) {
                            centerVerticallyTo(parent)
                            end.linkTo(parent.end, paddingSmall)
                        }
                )
            }
        }

        if (visible) {
            LazyColumn(
                modifier = Modifier
                    .testTag("comboBoxList")
                    .fillMaxWidth()
                    .height((visibleCount * buttonNormal.value).dp)
            ) {
                itemsIndexed(elements) { index, item ->
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                onValueChange(index)
                                visible = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonNormal),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
                    ) {
                        Text(
                            text = item.toString().uppercase(),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompoBox() {
    ThemedPreview {
        CompoBox(
            onValueChange = { },
            selected = 0,
            elements = listOf(
                stringResource(id = R.string.label_moto_type_single),
                stringResource(id = R.string.label_moto_type_recurring)
            ),
            modifier = Modifier.padding(paddingSmall),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            label = stringResource(id = R.string.label_select_moto_type)
        )
    }
}