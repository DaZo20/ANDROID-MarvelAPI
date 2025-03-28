package com.dmolaya.dev.marvelapi.characters.domain

import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
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
        }
    }

}