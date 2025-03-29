package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
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


class CharactersPagingSourceByNameTest {

    private val dataSourceImpl: CharactersDataSourceImpl = mockk()
    private val pagingSource = CharactersPagingSource(dataSourceImpl)

    @Test
    fun `load should return LoadResult Page when data is fetched successfully` () = runBlocking {
        //Given
        val name = "Spider-Man"
        val charactersDto = listOf(
            dummyCharacterDto,
            dummyCharacterDto2
        )

        coEvery { dataSourceImpl.getCharactersByName(name, any()) } returns Result.success(charactersDto)
        val pagingSource = CharactersPagingSourceByName(name, dataSourceImpl)

        //When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(charactersDto.size, pageResult.data.size)
        assertNull(pageResult.prevKey)
        assertNotNull(pageResult.nextKey)
    }

    @Test
    fun `load should return LoadResult Error when an exception occurs`() = runBlocking {
        //Given
        val name = "Spider-Man"
        val exception = IOException("Network error")

        coEvery { dataSourceImpl.getCharactersByName(any(), any()) } throws exception
        val pagingSource = CharactersPagingSourceByName(name, dataSourceImpl)

        //When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
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

val dummyCharacterDto = CharacterDto(
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

val dummyCharacterDto2 = CharacterDto(
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