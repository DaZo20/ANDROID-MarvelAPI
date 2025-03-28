package com.dmolaya.dev.marvelapi.characters.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CharactersViewModel @Inject constructor(
    @Named("get_characters_by_page") private val getCharactersByPageUC: CharacterDomainLayerContract.PresentationLayer.UseCase<Character>
) : ViewModel() {

    var characters: Flow<PagingData<Character>> = flowOf(PagingData.empty())

    init {
        getCharactersByPage()
    }

    private fun getCharactersByPage() {
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
}


