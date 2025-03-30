package com.dmolaya.dev.marvelapi.characters.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByIdUC
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterComicsUC
import com.dmolaya.dev.marvelapi.core.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    @Named("get_character_by_id") private val getCharacterByIdUC: GetCharacterByIdUC,
    @Named("get_character_comics")  private val getCharacterComicsUC: GetCharacterComicsUC
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _character = MutableStateFlow<Character>(Character.EMPTY)
    val character: StateFlow<Character> = _character

    var comics: Flow<PagingData<Comic>> = flowOf(PagingData.empty())


    fun resetUiState() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
        }
    }

    fun loadCharacterById(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val character = getCharacterByIdUC(characterId)
                // Utiliza un flag para evitar que se repita la carga de los cómics
                var isComicsLoaded = false
                character.collect { result ->
                    result.onSuccess { character ->
                        if (!isComicsLoaded) {
                            comics = getCharacterComicsUC(character.id)
                                .catch { e ->
                                    when (e) {
                                        is HttpException -> Log.e("CharactersViewModel", "Error HTTP: ${e.code()} - ${e.message()}")
                                        is IOException -> Log.e("CharactersViewModel", "Error de red: ${e.message}")
                                        else -> Log.e("CharactersViewModel", "Error inesperado: ${e.message}")
                                    }
                                }
                                .cachedIn(viewModelScope)
                            isComicsLoaded = true
                            _character.value = character
                            _uiState.value = UiState.Success(character)
                        }
                    }
                        .onFailure { error ->
                            val errorMessage = error.message ?: "Error desconocido"
                            _uiState.value = UiState.Error(errorMessage)
                        }
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Error desconocido"
                _uiState.value = UiState.Error(errorMessage)
            }
        }
    }

}