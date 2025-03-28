package com.dmolaya.dev.marvelapi.characters.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersPagingSource
import com.dmolaya.dev.marvelapi.characters.domain.CharacterDomainLayerContract
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiService: CharactersApiService,
): CharacterDomainLayerContract.DataLayer.CharactersRepository{

    companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 0
    }

    override suspend fun getCharactersByPage(): Flow<PagingData<Character>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS), pagingSourceFactory = {
            CharactersPagingSource(apiService)
        }).flow
    }

}