package org.goodexpert.apps.smartpay.ui.purchase

import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.goodexpert.apps.smartpay.MainActivity
import org.goodexpert.apps.smartpay.ui.theme.paddingNone
import org.goodexpert.apps.smartpay.ui.util.ThemedPreview
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PurchaseScreenTest {
    val tag = PurchaseScreenTest::class.java.simpleName

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            ThemedPreview {
                PurchaseScreen(
                    onDismiss = { },
                    amount = 200.0,
                    contentPadding = paddingNone
                )
            }
        }
    }

    @Test
    fun purchaseScreenTest() {
        composeTestRule
            .onNode(hasTestTag("amount"), true)
            .assertIsDisplayed()

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }

    @Test
    fun purchaseScreenTest_visualTransformationCardPan() {
        val container = composeTestRule
            .onNode(hasTestTag("pan"), true)
            .onChildren()
            .filter(hasTestTag("container"))
            .onFirst()

        Thread.sleep(500L)
        container
            .onChildren()
            .filter(hasSetTextAction())
            .onFirst()
            .also { node ->
                node.performTextInput("5456789912345670")
                node.assertContentDescriptionContains("5456-****-****-5670")
            }

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }

    @Test
    fun purchaseScreenTest_visualTransformationExpiryDate() {
        val container = composeTestRule
            .onNode(hasTestTag("expiryDate"), true)
            .onChildren()
            .filter(hasTestTag("container"))
            .onFirst()

        Thread.sleep(500L)
        container
            .onChildren()
            .filter(hasSetTextAction())
            .onFirst()
            .also { node ->
                node.performTextInput("1024")
                node.assertContentDescriptionContains("10/24")
            }

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }

    @Test
    fun purchaseScreenTest_visualTransformationCvv() {
        val container = composeTestRule
            .onNode(hasTestTag("cvv"), true)
            .onChildren()
            .filter(hasTestTag("container"))
            .onFirst()

        Thread.sleep(500L)
        container
            .onChildren()
            .filter(hasSetTextAction())
            .onFirst()
            .also { node ->
                node.performTextInput("123")
                node.assertContentDescriptionContains("123")
            }

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }

    @Test
    fun purchaseScreenTest_changeMotoType() {
        val comboBox = composeTestRule
            .onNode(hasTestTag("motoType"), true)
            .assertIsDisplayed()
            .assertContentDescriptionContains("SINGLE MOTO")
            .performClick()

        Thread.sleep(500L)
        composeTestRule
            .onNode(hasTestTag("comboBoxList"), true)
            .assertExists()
            .also { node ->
                node.onChildAt(1).performClick()
                node.assertDoesNotExist()
            }

        comboBox.assertContentDescriptionContains("RECURRING MOTO")

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }
}