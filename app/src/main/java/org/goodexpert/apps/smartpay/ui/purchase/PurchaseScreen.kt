package org.goodexpert.apps.smartpay.ui.purchase

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import com.github.zsoltk.compose.router.Router
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.goodexpert.apps.smartpay.R
import org.goodexpert.apps.smartpay.model.CardDetails
import org.goodexpert.apps.smartpay.ui.Routing
import org.goodexpert.apps.smartpay.ui.component.CheckButton
import org.goodexpert.apps.smartpay.ui.component.CompoBox
import org.goodexpert.apps.smartpay.ui.component.ProgressIndicator
import org.goodexpert.apps.smartpay.ui.component.TextField
import org.goodexpert.apps.smartpay.ui.component.cardNumberTransformedText
import org.goodexpert.apps.smartpay.ui.component.monthYearTransformedText
import org.goodexpert.apps.smartpay.ui.theme.ChateauGreen
import org.goodexpert.apps.smartpay.ui.theme.DarkCerulean
import org.goodexpert.apps.smartpay.ui.theme.PureWhite
import org.goodexpert.apps.smartpay.ui.theme.Shapes
import org.goodexpert.apps.smartpay.ui.theme.WildWillow
import org.goodexpert.apps.smartpay.ui.theme.amountTextPadding
import org.goodexpert.apps.smartpay.ui.theme.buttonNormal
import org.goodexpert.apps.smartpay.ui.theme.paddingNone
import org.goodexpert.apps.smartpay.ui.theme.paddingSmall
import org.goodexpert.apps.smartpay.ui.theme.paddingTiny
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview
import org.goodexpert.apps.smartpay.viewmodel.MotoViewModel
import java.text.NumberFormat

private fun isValidCvv(text: String): Boolean {
    return text.isEmpty() || text.length == 3
}

private fun isValidExpiryDate(text: String): Boolean {
    val regex = """(0?[1-9]|10|11|12)\d{2}""".toRegex()
    return text.isEmpty() || regex.matches(text)
}

private fun isValidPan(text: String): Boolean {
    return text.isEmpty() || text.length == 16
}

@Composable
fun PurchaseScreen(
    onDismiss: () -> Unit,
    amount: Number,
    contentPadding: PaddingValues,
    viewModel: MotoViewModel = hiltViewModel(),
    defaultRouting: Routing = Routing.PurchaseMainScreen
) {
    val backPressHandler = LocalBackPressHandler.current
    val coroutineScope = rememberCoroutineScope()
    var brush by remember { mutableStateOf(Brush.linearGradient(listOf(WildWillow, ChateauGreen))) }
    var colorText by remember { mutableStateOf(PureWhite) }
    var cardDetails by remember { mutableStateOf(CardDetails()) }
    var isProcessing by remember { mutableStateOf(false) }

    val isEnabled: () -> Boolean = {
        isValidPan(cardDetails.pan) && cardDetails.pan.isNotEmpty()
                && isValidExpiryDate(cardDetails.expiryDate) && cardDetails.expiryDate.isNotEmpty()
                && isValidCvv(cardDetails.cvv) && cardDetails.cvv.isNotEmpty()
    }

    DisposableEffect(Unit) {
        onDispose {
            backPressHandler.children.removeLast()
        }
    }

    Router(defaultRouting = defaultRouting) { backStack ->
        val currentRouting = backStack.last()

        LaunchedEffect(Unit) {
            backPressHandler.children.add {
                if (!isProcessing) {
                    onDismiss()
                }
                true
            }

            viewModel.uiEffect.collect {
                when (it) {
                    is MotoViewModel.Effect.PurchasedResult -> {
                        if (isProcessing) {
                            backStack.replace(Routing.PurchasedResultScreen(it.isSuccess))
                            isProcessing = false
                        }
                    }
                }
            }
        }

        ConstraintLayout(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            val (body, footer) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom, buttonNormal)
                    }
                    .fillMaxSize(),
                contentPadding = PaddingValues(paddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val nf = NumberFormat.getCurrencyInstance()

                    Text(
                        text = nf.format(amount),
                        modifier = Modifier
                            .testTag("amount")
                            .padding(amountTextPadding),
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                item {
                    CardDetailsEditor(
                        onValueChange = {
                            cardDetails = it
                            if (cardDetails.isRecurring) {
                                brush = Brush.linearGradient(listOf(PureWhite, PureWhite))
                                colorText = DarkCerulean
                            } else {
                                brush = Brush.linearGradient(listOf(WildWillow, ChateauGreen))
                                colorText = PureWhite
                            }
                        },
                        value = cardDetails
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(buttonNormal))
                }
            }

            TextButton(
                onClick = {
                    coroutineScope.launch {
                        backStack.push(Routing.ProcessingScreen)
                        isProcessing = true
                        viewModel.purchase(amount, cardDetails)
                    }
                },
                modifier = Modifier
                    .background(brush = brush)
                    .constrainAs(footer) {
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .height(buttonNormal),
                enabled = isEnabled()
            ) {
                Text(
                    text = stringResource(id = R.string.btn_continue).uppercase(),
                    color = colorText,
                    style = MaterialTheme.typography.button.copy(shadow = Shadow(offset = Offset(x = 0f, y = 4f), blurRadius = 8f))
                )
            }

            Crossfade(currentRouting) { routing ->
                when (routing) {
                    is Routing.ProcessingScreen -> {
                        Dialog(
                            onDismissRequest = {
                                coroutineScope.launch {
                                    backStack.pop()
                                    isProcessing = false
                                }
                            }
                        ) {
                            ProgressIndicator(text = stringResource(id = R.string.label_processing))
                        }
                    }
                    is Routing.PurchasedResultScreen -> {
                        val stringId = if (routing.isSuccess) R.string.message_purchase_success else R.string.message_purchase_error
                        AlertDialog(
                            onDismissRequest = {
                                coroutineScope.launch {
                                    backStack.pop()
                                }
                            },
                            text = {
                                Text(
                                    text = stringResource(id = stringId),
                                    color = MaterialTheme.colors.onSurface,
                                    style = MaterialTheme.typography.body1
                                )
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            backStack.pop()
                                        }
                                    }
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.btn_confirm),
                                        color = MaterialTheme.colors.secondary,
                                        style = MaterialTheme.typography.button
                                    )
                                }
                            },
                            backgroundColor = MaterialTheme.colors.surface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StoreCredentials(
    onValueChange: (Boolean) -> Unit,
    value: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .testTag("storeCredentials")
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = stringResource(id = R.string.label_stored_credentials),
            modifier = Modifier
                .testTag("label")
                .padding(paddingSmall),
            style = MaterialTheme.typography.body1
        )

        Row(
            modifier = Modifier
                .testTag("container")
                .fillMaxWidth()
                .padding(paddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_card_stored_on_file),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )

            CheckButton(
                onClick = {
                    coroutineScope.launch {
                        onValueChange(true)
                    }
                },
                text = stringResource(id = R.string.btn_yes),
                isChecked = (value == true),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
            )

            Spacer(modifier = Modifier.padding(paddingTiny))

            CheckButton(
                onClick = {
                    coroutineScope.launch {
                        onValueChange(false)
                    }
                },
                text = stringResource(id = R.string.btn_no),
                isChecked = (value != true),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
            )
        }
    }
}

@Composable
private fun CardDetailsEditor(
    onValueChange: (CardDetails) -> Unit,
    value: CardDetails
) {
    val coroutineScope = rememberCoroutineScope()
    var motoType by remember { mutableStateOf(0) }
    var colorCombo by remember { mutableStateOf(ChateauGreen) }
    val focusCvv = FocusRequester()
    val focusExpiryDate = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TextField(
            value = value.pan,
            onValueChange = {
                coroutineScope.launch {
                    if (it.length <= 16) {
                        onValueChange(value.copy(pan = it))
                    }
                }
            },
            modifier = Modifier
                .testTag("pan")
                .fillMaxWidth()
                .wrapContentHeight(),
            label = stringResource(id = R.string.label_enter_card_pan),
            placeholder = "XXXX-XXXX-XXXX-XXXX",
            isError = !isValidPan(value.pan),
            visualTransformation = {
                cardNumberTransformedText(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusExpiryDate.requestFocus() }
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextField(
                value = value.expiryDate,
                onValueChange = {
                    coroutineScope.launch {
                        if (it.length <= 4) {
                            onValueChange(value.copy(expiryDate = it))
                        }
                    }
                },
                modifier = Modifier
                    .testTag("expiryDate")
                    .focusRequester(focusExpiryDate)
                    .wrapContentHeight()
                    .weight(1f),
                label = stringResource(id = R.string.label_expiry_date),
                placeholder = "XX/XX",
                isError = !isValidExpiryDate(value.expiryDate),
                visualTransformation = {
                    monthYearTransformedText(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusCvv.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(paddingTiny))

            TextField(
                value = value.cvv,
                onValueChange = {
                    coroutineScope.launch {
                        if (it.length <= 3) {
                            onValueChange(value.copy(cvv = it))
                        }
                    }
                },
                modifier = Modifier
                    .testTag("cvv")
                    .focusRequester(focusCvv)
                    .wrapContentHeight()
                    .weight(1f),
                label = stringResource(id = R.string.label_security_code),
                placeholder = "XXX",
                isError = !isValidCvv(value.cvv),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }

        CompoBox(
            onValueChange = {
                coroutineScope.launch {
                    if (motoType != it) {
                        motoType = it
                        colorCombo = if (motoType == 0) ChateauGreen else DarkCerulean
                        onValueChange(value.copy(isRecurring = (motoType != 0)))
                    }
                }
            },
            selected = motoType,
            elements = listOf(
                stringResource(id = R.string.label_moto_type_single),
                stringResource(id = R.string.label_moto_type_recurring)
            ),
            modifier = Modifier.testTag("motoType"),
            colors = ButtonDefaults.buttonColors(colorCombo),
            label = stringResource(id = R.string.label_select_moto_type)
        )

        if (motoType == 1) {
            StoreCredentials(
                onValueChange = {
                    coroutineScope.launch {
                        if (value.isSavedOnFile != it) {
                            onValueChange(value.copy(isSavedOnFile = it))
                        }
                    }
                },
                value = value.isSavedOnFile
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPurchaseScreen() {
    ThemedPreview {
        PurchaseScreen(
            onDismiss = { },
            amount = 200.0,
            contentPadding = paddingNone
        )
    }
}