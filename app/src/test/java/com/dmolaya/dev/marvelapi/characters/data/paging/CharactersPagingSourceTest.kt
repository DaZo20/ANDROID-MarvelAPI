package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import com.dmolaya.dev.marvelapi.core.utils.toCharacterList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharactersPagingSourceTest {
    private val dataSourceImpl: CharactersDataSourceImpl = mockk()
    private val pagingSource = CharactersPagingSource(dataSourceImpl)

    @Test
    fun `load should return LoadResult Page when data is available`() = runBlocking {
        //Given
        val page = 0
        val characterList = listOf(
            dummyCharacter,
            dummyCharacter2
        )

        coEvery { dataSourceImpl.getCharactersByPage(page * 20) } returns Result.success(characterList)

        //When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        //Then
        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(characterList.toCharacterList(), pageResult.data)
        assertEquals(null, pageResult.prevKey)
        assertEquals(1, pageResult.nextKey)
    }

}

val dummyCharacter = CharacterDto(
    id = 1,
    name = "Spider-Man",
    description = "Peter Parker",
    comics = ComicsDto(
        available = 10,
        collectionURI = "http://example.com",
        items = listOf(),
        returned = 10
    ),
    thumbnail = ThumbnailDto(
        path = "http://example.com",
        extension = "jpg"
    ),
    modified = "2023-10-01T00:00:00Z"
)

val dummyCharacter2 = CharacterDto(
    id = 2,
    name = "Hulk",
    description = "Bruce Banner",
    comics = ComicsDto(
        available = 100,
        collectionURI = "http://example2.com",
        items = listOf(),
        returned = 1
    ),
    thumbnail = ThumbnailDto(
        path = "http://example2.com",
        extension = "jpg"
    ),
    modified = "2023-10-01T00:00:00Z"
)

