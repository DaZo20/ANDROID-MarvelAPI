package com.dmolaya.dev.marvelapi.characters.domain.di

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharactersByPageUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDomainDependencies {

    @Provides
    @Singleton
    @Named("get_characters_by_page")
    fun providesGetCharactersByPageUC(
        charactersRepository: CharacterDomainLayerContract.DataLayer.CharactersRepository
    ): CharacterDomainLayerContract.PresentationLayer.UseCase<Character> {
        return GetCharactersByPageUC(charactersRepository)
    }

}