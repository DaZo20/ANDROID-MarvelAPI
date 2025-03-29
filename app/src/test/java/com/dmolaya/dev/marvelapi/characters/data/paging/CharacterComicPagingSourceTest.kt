package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDateDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test


class CharacterComicPagingSourceTest {
    private val dataSourceImpl: CharactersDataSourceImpl = mockk()
    private val pagingSource = CharactersPagingSource(dataSourceImpl)

    @Test
    fun `load should return LoadResult Page when comics are fetched successfully`() = runBlocking {
        //Given
        val characterId = 1
        val comics = listOf(
            dummyComic,
            dummyComic2
        )

        coEvery { dataSourceImpl.getComicsByCharacterId(characterId, any()) } returns Result.success(comics)
        val pagingSource = CharacterComicPagingSource(characterId, dataSourceImpl)

        //When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(comics.size, pageResult.data.size)
        assertNull(pageResult.prevKey)
        assertNotNull(pageResult.nextKey)
    }

    @Test
    fun `load should return LoadResult Error when an exception occurs`() = runBlocking {
        //Given
        val exception = IOException("Network error")

        coEvery { dataSourceImpl.getComicsByCharacterId(any(), any()) } throws exception
        val pagingSource = CharacterComicPagingSource(1, dataSourceImpl)

        //When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals("Network error", errorResult.throwable.message)
    }
}

val dummyComic = ComicDto(
    id = 1,
    title = "The Amazing Spider-Man",
    thumbnail = ThumbnailDto(
        path = "http://example.com/ultimatespiderman",
        extension = "jpg"
    ),
    dates = listOf(
        ComicDateDto(
            date = "2023-10-01",
            type = "onsaleDate"
        )
    )
)

val dummyComic2 = ComicDto(
    id = 2,
    title = "Ultimate Spider-Man",
    thumbnail = ThumbnailDto(
        path = "http://example.com/ultimatespiderman",
        extension = "jpg"
    ),
    dates = listOf(
        ComicDateDto(
        date = "2023-10-01",
        type = "onsaleDate"
    )
    )
)