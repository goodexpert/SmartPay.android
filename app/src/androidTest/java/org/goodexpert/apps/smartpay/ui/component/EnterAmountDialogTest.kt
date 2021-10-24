package org.goodexpert.apps.smartpay.ui.component

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.goodexpert.apps.smartpay.MainActivity
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EnterAmountDialogTest {
    val tag = EnterAmountDialogTest::class.java.simpleName

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            ThemedPreview {
                EnterAmountDialog(
                    onConfirm = { },
                    onDismiss = { }
                )
            }
        }
    }

    @Test
    fun enterAmountDialogTest() {
        val textField = composeTestRule
            .onNode(hasTestTag("textField"), true)

        Thread.sleep(500L)
        textField.performTextInput("200.00")
        textField.assertTextEquals("200.00")

        composeTestRule.onNode(hasTestTag("alertDialog")).printToLog(tag)
        Thread.sleep(1000L)
    }
}