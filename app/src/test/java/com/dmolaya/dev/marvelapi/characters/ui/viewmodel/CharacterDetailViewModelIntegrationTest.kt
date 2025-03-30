package com.dmolaya.dev.marvelapi.characters.ui.viewmodel

import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.repository.CharactersRepository
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.characters.domain.model.ComicDate
import com.dmolaya.dev.marvelapi.characters.domain.model.Comics
import com.dmolaya.dev.marvelapi.characters.domain.model.Item
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByIdUC
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterComicsUC
import com.dmolaya.dev.marvelapi.core.utils.UiState
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class CharacterDetailViewModelIntegrationTest {

    private lateinit var apiService: CharactersApiService
    private lateinit var dataSource: CharactersDataSourceImpl
    private lateinit var repository: CharactersRepository
    private lateinit var getCharacterByIdUC: GetCharacterByIdUC
    private lateinit var getCharacterComicsUC: GetCharacterComicsUC
    private lateinit var detailViewModel: CharacterDetailViewModel

    @Before
    fun setup() {
        apiService = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApiService::class.java)

        // Usa el DataSource real
        dataSource = CharactersDataSourceImpl(apiService)

        // Usa el Repository real
        repository = CharactersRepository(dataSource)

        // Instancia los UseCases con el repository real
        getCharacterByIdUC = GetCharacterByIdUC(repository)
        getCharacterComicsUC = GetCharacterComicsUC(repository)

        // Crea el ViewModel con los casos de uso reales
        detailViewModel = CharacterDetailViewModel(getCharacterByIdUC, getCharacterComicsUC)
    }

    @Test
    fun `when a character is loaded from API, the state should be Success`() = runBlocking {
        // Act
        detailViewModel.loadCharacterById(1009368) // ID de Iron Man en la API de Marvel

        // Esperar a que la API responda
        delay(5000)

        // Assert: Verifica que el estado sea Success con un personaje real
        assertTrue(detailViewModel.uiState.value is UiState.Success<*>)
    }

    @Test
    fun `when an invalid character ID is used, the state should be Error`() = runBlocking {
        // Act
        detailViewModel.loadCharacterById(-1) // ID inválido

        // Esperar a que la API responda
        delay(5000)

        // Assert: Verifica que el estado sea Error
        assertTrue(detailViewModel.uiState.value is UiState.Error)
    }
}