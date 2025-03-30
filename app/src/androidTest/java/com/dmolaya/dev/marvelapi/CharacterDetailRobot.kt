package com.dmolaya.dev.marvelapi

import androidx.compose.ui.semantics.text
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag

/*https://proandroiddev.com/end-to-end-testing-with-robot-pattern-and-jetpack-compose-a001aeef415f*/
class CharacterDetailRobot(private val composeRule: ComposeTestRule) {
    //Verify that the characterImage is visible

    fun checkCharacterImageVisible(): CharacterDetailRobot {
        composeRule.waitUntil(5000L) {
            // Espera a que el nodo con la imagen del personaje esté visible
            composeRule.onAllNodesWithTag("character_detail_image_tag").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithTag("character_detail_image_tag")
            .assertIsDisplayed()  // Asegura que el nodo está visible
        return this
    }

    //Verify that the name of the character is visible
    fun checkCharacterNameVisible(): CharacterDetailRobot {
        composeRule.onNodeWithTag("character_detail_name_tag")
            .assertIsDisplayed()
        return this
    }

    //If description exists then verify
    fun checkCharacterDescriptionIfExists(): CharacterDetailRobot {
        val descriptionNode = composeRule.onNodeWithTag("character_detail_description_tag")
        descriptionNode.assertIsDisplayed()
        return this
    }

    //If comics exist then verify if exists
    fun checkComicsList(): CharacterDetailRobot {
        composeRule.waitUntil(5000L) {
            composeRule.onAllNodesWithTag("character_detail_comic_tag").fetchSemanticsNodes()
                .isNotEmpty()
        }
        return this
    }


}