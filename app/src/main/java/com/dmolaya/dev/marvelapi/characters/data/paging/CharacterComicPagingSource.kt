package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.core.utils.toComicList
import javax.inject.Inject

class CharacterComicPagingSource @Inject constructor(
    private val characterId: Int,
    private val dataSource: CharactersDataSourceImpl
): PagingSource<Int, Comic>(){

    override fun getRefreshKey(state: PagingState<Int, Comic>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        return try {
            val page = params.key ?: 0
            val result = dataSource.getComicsByCharacterId(characterId, page * 20)
            val comics = result.getOrNull()?.toComicList() ?: emptyList()

            LoadResult.Page(
                data = comics,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (comics.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}