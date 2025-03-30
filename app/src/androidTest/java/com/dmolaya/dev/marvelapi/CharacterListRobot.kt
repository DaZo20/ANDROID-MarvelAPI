package com.dmolaya.dev.marvelapi

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp

/*https://proandroiddev.com/end-to-end-testing-with-robot-pattern-and-jetpack-compose-a001aeef415f*/
class CharacterListRobot(private val composeRule: ComposeTestRule) {
    //Method that verify that the name of a character
    fun checkCharacterName(name: String): CharacterListRobot {
        composeRule.onNodeWithText(name)
            .assertIsDisplayed()
        return this
    }

    //Method to press on specific character of the list
    fun clickOnCharacterAtPosition(position: Int): CharacterListRobot {
        composeRule.onAllNodesWithTag("character_item_tag")[position]
            .assertExists()
            .performClick()
        return this
    }

    //Method to verify that the characterList is not empty
    fun checkListIsNotEmpty(): CharacterListRobot {
        composeRule.waitUntil(5000L) {
            composeRule.onAllNodesWithTag("character_item_tag").fetchSemanticsNodes().isNotEmpty()
        }
        return this
    }

    //Method to simulate scroll down
    fun scrollDown(): CharacterListRobot {
        composeRule.onRoot().performTouchInput { swipeUp() }
        return this
    }
}