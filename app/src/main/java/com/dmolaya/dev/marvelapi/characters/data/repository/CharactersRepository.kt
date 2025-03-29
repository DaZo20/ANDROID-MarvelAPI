package com.dmolaya.dev.marvelapi.characters.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.paging.CharacterComicPagingSource
import com.dmolaya.dev.marvelapi.characters.data.paging.CharactersPagingSource
import com.dmolaya.dev.marvelapi.characters.data.paging.CharactersPagingSourceByName
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.core.utils.toCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val dataSourceImpl: CharactersDataSourceImpl
): CharacterDomainLayerContract.DataLayer.CharactersRepository{

    companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 1
    }

    override suspend fun getCharactersByPage(): Flow<PagingData<Character>> =
        Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS), pagingSourceFactory = {
            CharactersPagingSource(dataSourceImpl)
        }).flow


    override suspend fun getCharacterById(id: Int): Flow<Result<Character>> =
        flow {
            val result = dataSourceImpl.getCharacterById(id)

            // Maneja el resultado: éxito o error
            if (result.isSuccess) {
                val characterDto = result.getOrNull()
                if (characterDto != null) {
                    emit(Result.success(characterDto.toCharacter()))
                } else {
                    emit(Result.failure<Character>(Exception("Character not found")))
                }
            } else {
                // En caso de error, emite un resultado de fallo
                emit(Result.failure<Character>(result.exceptionOrNull() ?: Exception("Unknown error")))
            }
        }

    override suspend fun getCharacterByName(name: String): Flow<PagingData<Character>> =
        Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS), pagingSourceFactory = {
            CharactersPagingSourceByName(name, dataSourceImpl)
        }).flow

    override suspend fun getCharacterComics(id: Int): Flow<PagingData<Comic>> =
        Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS), pagingSourceFactory = {
            CharacterComicPagingSource(id, dataSourceImpl)
        }).flow


}