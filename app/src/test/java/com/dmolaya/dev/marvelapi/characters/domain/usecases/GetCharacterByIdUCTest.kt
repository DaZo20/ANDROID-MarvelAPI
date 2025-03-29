package com.dmolaya.dev.marvelapi.characters.domain.usecases

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.assertEquals
class GetCharacterByIdUCTest {

    @RelaxedMockK
    private lateinit var characterRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository

    private lateinit var getCharacterByIdUC: GetCharacterByIdUC

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCharacterByIdUC = GetCharacterByIdUC(characterRepository)
    }

    @Test
    fun `When the character by id is requested, and the API response successful, a 'Character' is returned`() =
        runBlocking {
            // Given
            val characterId = 1
            val expectedCharacter = dummyCharacter

            // When
            coEvery { characterRepository.getCharacterById(characterId) } returns flowOf(
                Result.success(
                    expectedCharacter
                )
            )

            // Then
            val result = getCharacterByIdUC(characterId).first()
            assertEquals(Result.success(expectedCharacter), result)
        }

    @Test
    fun `When the character by id is requested, and the API response fails, an error is returned`() =
        runBlocking {
            // Given
            val characterId = 1
            val expectedError = "Character not found"

            // When
            coEvery { characterRepository.getCharacterById(characterId) } returns flowOf(
                Result.failure(
                    Exception(expectedError)
                )
            )

            // Then
            val result = getCharacterByIdUC(characterId).first()
            assert(result.isFailure)
            assertEquals(expectedError, result.exceptionOrNull()?.message)
        }

    @Test
    fun `When the character by id is requested, and no character is found, a failure result is returned`() = runBlocking {
        // Given
        val characterId = 1
        val expectedError = "Character not found"

        // When
        coEvery { characterRepository.getCharacterById(characterId) } returns flowOf(
            Result.failure(
                Exception(expectedError)
            )
        )

        // Then
        val result = getCharacterByIdUC(characterId).first()
        assert(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull()?.message)
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