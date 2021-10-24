package org.goodexpert.apps.smartpay.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
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
class HomeScreenTest {
    val tag = HomeScreenTest::class.java.simpleName

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            ThemedPreview {
                HomeScreen(
                    onClick = { },
                    contentPadding = paddingNone
                )
            }
        }
    }

    @Test
    fun homeScreenTest() {
        composeTestRule
            .onNodeWithText("Purchase with New Card")
            .assertIsDisplayed()

        composeTestRule.onRoot().printToLog(tag)
        Thread.sleep(1000L)
    }
}