package com.dmolaya.dev.marvelapi.characters.domain.usecases

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import javax.inject.Inject

class GetCharacterByNameUC @Inject constructor(
    private val charactersRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository
) {
    suspend operator fun invoke(name: String) =
        charactersRepository.getCharacterByName(name)
}