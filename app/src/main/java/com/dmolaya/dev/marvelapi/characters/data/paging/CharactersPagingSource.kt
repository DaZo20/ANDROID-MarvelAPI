package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.core.utils.toCharacter
import com.dmolaya.dev.marvelapi.core.utils.toCharacterList
import okio.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val dataSourceImpl: CharactersDataSourceImpl
): PagingSource<Int, Character>(){

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {

            val page = params.key ?: 0
            val response = dataSourceImpl.getCharactersByPage(page * 20)
            val characters = response.getOrNull()?.toCharacterList() ?: emptyList()

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (characters.isEmpty()) null else page + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }
}