package com.dmolaya.dev.marvelapi.characters.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.core.utils.toCharacter
import okio.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val apiService: CharactersApiService
): PagingSource<Int, Character>(){

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {

            val page = params.key ?: 0
            val response = apiService.getCharactersByPage(page * 20)
            val characters = response.data.results

            LoadResult.Page(
                data = characters.map { it.toCharacter() },
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