package org.goodexpert.apps.smartpay.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
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
class SmartPayAppTest {
    val tag = SmartPayAppTest::class.java.simpleName

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            ThemedPreview {
                MainContent()
            }
        }
    }

    @Test
    fun smartPayAppTest() {
        composeTestRule
            .onNodeWithText("Purchase with New Card")
            .assertIsDisplayed()
            .performClick()

        Thread.sleep(500L)
        composeTestRule
            .onNode(hasTestTag("textField"), true)
            .also { node ->
                node.performTextInput("200.00")
                node.assertTextEquals("200.00")
            }

        Thread.sleep(500L)
        composeTestRule
            .onNode(hasTestTag("confirmButton"), true)
            .performClick()

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(5000L)
    }
}