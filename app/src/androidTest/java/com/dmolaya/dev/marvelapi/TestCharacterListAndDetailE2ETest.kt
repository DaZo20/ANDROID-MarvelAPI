package com.dmolaya.dev.marvelapi

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import org.junit.Rule
import org.junit.Test


class TestCharacterListAndDetailE2ETest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun wholeAndroidFlowTest() {
        ActivityScenario.launch(MainActivity::class.java)
        // 1. Splash Screen appears (assuming the splash disappears at some point)
        // Here you assume the Splash Screen has an implicit wait
        // or its content disappears to show the character list.
        SplashScreenRobot(composeRule)
            .checkLogoVisible()

        // 2. The character list is loaded
        Thread.sleep(2000)
        composeRule.waitForIdle()
        CharacterListRobot(composeRule)
            .checkListIsNotEmpty() // Verifies the list is not empty

        // 3. Scroll down the list
        CharacterListRobot(composeRule)
            .scrollDown()

        // 4. Click on a random item
        val totalItems = composeRule.onAllNodesWithTag("character_item_tag").fetchSemanticsNodes().size
        val positionToClick = if (totalItems > 0) totalItems - 1 else 0
        CharacterListRobot(composeRule)
            .clickOnCharacterAtPosition(positionToClick)

        // 5. View the details of that character
        CharacterDetailRobot(composeRule)
            .checkCharacterImageVisible()
            .checkCharacterNameVisible()
            .checkCharacterDescriptionIfExists()  // Verifies if description exists
            .checkComicsList() // Verifies if comics exist

        // 6. Go back to the previous screen
        Espresso.pressBack()

        // 7. Perform a search for a character
        CharacterSearchRobot(composeRule)
            .clickOnSearchBar()
            .enterSearchQuery("Spider-Man")

        // 8. Click on a character from the search results
        CharacterSearchRobot(composeRule)
            .checkCharacterSearchedName("Spider-Man")
            .clickOnCharacterAtPosition(0) // Selects the first result, for example

        // 9. View the details of the searched character
        CharacterDetailRobot(composeRule)
            .checkCharacterImageVisible()
            .checkCharacterNameVisible()
            .checkCharacterDescriptionIfExists()  // Verifies if description exists
            .checkComicsList() // Verifies if comics exist
    }
}