package com.dmolaya.dev.marvelapi.characters.domain

import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface CharacterDomainLayerContract {

    interface PresentationLayer {
        interface UseCase<T : Any> {
            suspend operator fun invoke(): Flow<PagingData<T>>
        }
    }

    interface DataLayer {
        interface CharactersRepository {
            suspend fun getCharactersByPage(): Flow<PagingData<Character>>
            suspend fun getCharacterById(id: Int): Flow<Result<Character>>
            suspend fun getCharacterByName(name: String): Flow<PagingData<Character>>
            suspend fun getCharacterComics(id: Int): Flow<PagingData<Comic>>
        }
    }

}