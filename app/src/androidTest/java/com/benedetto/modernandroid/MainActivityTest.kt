package com.benedetto.modernandroid


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncrementButton(){
        composeTestRule.setContent { CounterScreen() }

        //Verify initial count is 0
        composeTestRule.onNodeWithText("Count: 0").assertExists()

        //click button and verify count updates
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Count: 1").assertExists()

    }


}