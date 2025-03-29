package com.dmolaya.dev.marvelapi.characters.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comics
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByIdUC
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterComicsUC
import com.dmolaya.dev.marvelapi.core.utils.UiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    @RelaxedMockK
    private lateinit var getCharacterByIdUC: GetCharacterByIdUC
    @RelaxedMockK
    private lateinit var getCharacterComicsUC: GetCharacterComicsUC

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()



    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        characterDetailViewModel = CharacterDetailViewModel(getCharacterByIdUC, getCharacterComicsUC)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadCharacterById should set loading state initially`() = runTest {
        //Given
        val characterId = 1

        //When
        characterDetailViewModel.loadCharacterById(characterId)

        //Then
        assert(characterDetailViewModel.uiState.value is UiState.Loading)
    }

    @Test
    fun `loadCharacterById should set success state when data is loaded successfully`() = runTest {
        //Given
        val characterId = 1
        val character = dummyCharacter
        coEvery { getCharacterByIdUC(characterId) } returns flowOf(Result.success(character))

        //When
        characterDetailViewModel.loadCharacterById(characterId)

        //Then
        assert(characterDetailViewModel.uiState.value is UiState.Success<*>)
        val successState = characterDetailViewModel.uiState.value as UiState.Success<*>
        assert(successState.data == character)

    }

    @Test
    fun `loadCharacterById should set error state when an error occurs`() = runTest {
        //Given
        val characterId = 1
        val errorMessage = "Error loading character"

        coEvery { getCharacterByIdUC(characterId) } returns flowOf(Result.failure(Exception(errorMessage)))

        //When
        characterDetailViewModel.loadCharacterById(characterId)
        //Then
        val state = characterDetailViewModel.uiState.value
        assert(state is UiState.Error)
        val errorState = state as UiState.Error
        assert(errorState.message == errorMessage)
    }

    @Test
    fun `sould call getCharacterComicsUC once character is loaded`() = runTest {
        //Given
        val characterId = 1
        val character = dummyCharacter
        coEvery { getCharacterByIdUC(characterId) } returns flowOf(Result.success(character))
        coEvery { getCharacterComicsUC(characterId) } returns flowOf(PagingData.empty())

        //When
        characterDetailViewModel.loadCharacterById(characterId)

        //Then
        coVerify(exactly = 1) { getCharacterComicsUC(character.id) }
    }

}

val dummyCharacter = Character(
    id = 1,
    name = "Spider-Man",
    description = "Peter Parker",
    comics = Comics(
        available = 10,
        collectionURI = "http://example.com",
        items = listOf(),
        returned = 10
    ),
    thumbnail = "http://example.com/image.jpg"
)