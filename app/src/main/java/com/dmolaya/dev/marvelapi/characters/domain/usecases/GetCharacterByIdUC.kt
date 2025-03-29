package com.dmolaya.dev.marvelapi.characters.domain.usecases

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import javax.inject.Inject

class GetCharacterByIdUC @Inject constructor(
    private val charactersRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository
) {
    suspend operator fun invoke(id: Int) =
        charactersRepository.getCharacterById(id)
}