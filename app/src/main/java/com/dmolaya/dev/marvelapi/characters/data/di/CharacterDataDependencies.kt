package com.dmolaya.dev.marvelapi.characters.data.di

import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.repository.CharactersRepository
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CharacterDataDependencies {

    @Provides
    fun providesCharacterDataRepository(dataSource: CharactersDataSourceImpl): CharacterDomainLayerContract.DataLayer.CharactersRepository =
        CharactersRepository(dataSource)


    @Provides
    fun providesCharacterDataSource(apiService: CharactersApiService): CharactersDataSourceImpl =
        CharactersDataSourceImpl(apiService)
}