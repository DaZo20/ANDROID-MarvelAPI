package com.dmolaya.dev.marvelapi

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

/*https://proandroiddev.com/end-to-end-testing-with-robot-pattern-and-jetpack-compose-a001aeef415f*/
class CharacterSearchRobot(private val composeRule: ComposeTestRule) {

    //Performs click on searchBar
    fun clickOnSearchBar(): CharacterSearchRobot {
        composeRule.onNodeWithTag("character_search_tag")
            .performClick()
        return this
    }

    //Performs search
    fun enterSearchQuery(query: String): CharacterSearchRobot {
        composeRule.onNodeWithTag("character_search_tag")
            .performTextInput(query)
        composeRule.waitForIdle()
        return this
    }

    //Method to press on specific character of the list
    fun clickOnCharacterAtPosition(position: Int): CharacterSearchRobot {
        composeRule.waitUntil(timeoutMillis = 10000L) {
            composeRule.onAllNodesWithTag("character_item_tag").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onAllNodesWithTag("character_item_tag")[position]
            .assertExists()
            .performClick()
        return this
    }

    //Method that verify that the name of a character
    fun checkCharacterSearchedName(name: String): CharacterSearchRobot {
        composeRule.waitUntil(timeoutMillis = 5000L) {
            composeRule.onAllNodesWithText(name).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText(name)
            .assertIsDisplayed()

        return this
    }

    //Verify empty view message
    fun checkEmptyStateVisible(): CharacterSearchRobot {
        composeRule.onNodeWithTag("empty_state_tag")
            .assertIsDisplayed()
        return this
    }
}