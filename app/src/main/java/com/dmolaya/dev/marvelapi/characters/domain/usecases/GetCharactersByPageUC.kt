package com.dmolaya.dev.marvelapi.characters.domain.usecases

import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersByPageUC @Inject constructor(
    private val charactersRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository
): CharacterDomainLayerContract.PresentationLayer.UseCase<Character>{

    override suspend fun invoke(): Flow<PagingData<Character>> =
        charactersRepository.getCharactersByPage()

}