package com.dmolaya.dev.marvelapi.characters.domain.usecases

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import javax.inject.Inject

class GetCharacterComicsUC @Inject constructor(
    private val charactersRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository
) {
    suspend operator fun invoke(id: Int) =
        charactersRepository.getCharacterComics(id)
}