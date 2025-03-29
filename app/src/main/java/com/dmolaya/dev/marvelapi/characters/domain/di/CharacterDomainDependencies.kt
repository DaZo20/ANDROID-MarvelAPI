package com.dmolaya.dev.marvelapi.characters.domain.di

import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByIdUC
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterByNameUC
import com.dmolaya.dev.marvelapi.characters.domain.usecases.GetCharacterComicsUC
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

    @Provides
    @Singleton
    @Named("get_character_by_name")
    fun providesGetCharacterByNameUC(
        getByNameUseCase: GetCharacterByNameUC
    ): GetCharacterByNameUC = getByNameUseCase

    @Provides
    @Singleton
    @Named("get_character_by_id")
    fun providesGetCharacterByIdUC(
        getByIdUseCase: GetCharacterByIdUC
    ): GetCharacterByIdUC = getByIdUseCase

    @Provides
    @Singleton
    @Named("get_character_comics")
    fun providesGetCharacterComicsUC(
        getComicsByCharacterIdUseCase: GetCharacterComicsUC
    ): GetCharacterComicsUC = getComicsByCharacterIdUseCase

}