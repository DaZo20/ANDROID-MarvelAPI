package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.core.utils.toCharacterList
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import javax.inject.Inject

class CharactersPagingSourceByName @Inject constructor(
    private val name: String,
    private val dataSource: CharactersDataSourceImpl
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 0
            val result = dataSource.getCharactersByName(name, page * 20)
            val characters = result.getOrNull()?.toCharacterList() ?: emptyList()

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (characters.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}