package com.dmolaya.dev.marvelapi.characters.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByNameUC
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
class CharactersViewModel @Inject constructor(
    @Named("get_characters_by_page") private val getCharactersByPageUC: CharacterDomainLayerContract.PresentationLayer.UseCase<Character>,
    @Named("get_character_by_name") private val getCharacterByNameUC: GetCharacterByNameUC
) : ViewModel() {

    var characters: Flow<PagingData<Character>> = flowOf(PagingData.empty())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    var lastQuery = ""
    // Función para actualizar el query
    fun updateSearchQuery(query: String) {
        if (query != lastQuery) {
            _searchQuery.value = query
            lastQuery = query
            if (query.isNotEmpty()) {
                getCharacterByName(query)
            } else {
                getCharactersByPage()
            }
        }
    }

    init {
        getCharactersByPage()
    }

    fun getCharactersByPage() {
        viewModelScope.launch {
            characters = getCharactersByPageUC()
                .catch { e ->
                    when (e) {
                        is HttpException -> Log.e("CharactersViewModel", "Error HTTP: ${e.code()} - ${e.message()}")
                        is IOException -> Log.e("CharactersViewModel", "Error de red: ${e.message}")
                        else -> Log.e("CharactersViewModel", "Error inesperado: ${e.message}")
                    }
                }
                .cachedIn(viewModelScope)
        }

    }

    fun getCharacterByName(name: String) {
        viewModelScope.launch {
            characters = getCharacterByNameUC(name)
                .catch { e ->
                    when (e) {
                        is HttpException -> Log.e("CharactersViewModel", "Error HTTP: ${e.code()} - ${e.message()}")
                        is IOException -> Log.e("CharactersViewModel", "Error de red: ${e.message}")
                        else -> Log.e("CharactersViewModel", "Error inesperado: ${e.message}")
                    }
                }
                .cachedIn(viewModelScope)
        }
    }
}


