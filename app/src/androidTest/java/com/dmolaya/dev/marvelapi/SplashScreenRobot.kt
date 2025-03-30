package com.dmolaya.dev.marvelapi

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

class SplashScreenRobot(private val composeRule: ComposeTestRule) {

    //Verify that image logo is visible
    fun checkLogoVisible(): SplashScreenRobot{
        composeRule.onNodeWithTag("splash_screen_logo_tag")
            .assertIsDisplayed()
        return this
    }

    //Verify characterList screen is displayed
    fun checkTransitionToCharacterListScreen(): SplashScreenRobot {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("scaffold_characters_tag")
            .assertIsDisplayed()
        return this
    }
}