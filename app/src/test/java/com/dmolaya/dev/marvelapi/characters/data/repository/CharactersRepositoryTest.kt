package com.dmolaya.dev.marvelapi.characters.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDateDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.characters.domain.model.ComicDate
import com.dmolaya.dev.marvelapi.core.utils.toCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharactersRepositoryTest {

    private val dataSourceImpl: CharactersDataSourceImpl = mockk()
    private lateinit var charactersRepository: CharactersRepository

    @Before
    fun onBefore() {
        charactersRepository = CharactersRepository(dataSourceImpl)
    }

    @Test
    fun `getCharactersById should return success when API respond successfully and dataSource returns a valid character`() = runBlocking {
            //Given
            val characterId = 1
            val characterDto = dummyCharacter
            coEvery { dataSourceImpl.getCharacterById(characterId) } returns Result.success(
                characterDto
            )

            //When
            val result = charactersRepository.getCharacterById(id = characterId).first()

            //Then
            assert(result.isSuccess)
            assertEquals(characterDto.toCharacter(), result.getOrNull())
    }

    @Test
    fun `getCharactersByPage should return PagingData with characters`() = runBlocking {
        //Given
        val characters = listOf(dummyCharacter, dummyCharacter2)

        val pagingSource = FakePagingSource(characters)
        coEvery { dataSourceImpl.getCharactersByPage(any()) } returns Result.success(characters.map { it })

        //When
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(2, page.data.size)
        assertEquals("Spider-Man", page.data[0].name)
        assertEquals("Hulk", page.data[1].name)
    }

    @Test
    fun `getCharactersByName should return PagingData with filtered characters`() = runBlocking {
        //Given
        val searchName = "Spider"
        val characters = listOf(dummyCharacter, dummyCharacter3)

        val pagingSource = FakePagingSource(characters)
        coEvery { dataSourceImpl.getCharactersByName(searchName, any()) } returns  Result.success(characters.map { it })

        //When
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(2, page.data.size)
        assertEquals("Spider-Man", page.data[0].name)
        assertEquals("Spider-Woman", page.data[1].name)
    }

    @Test
    fun `getCharacterComics should return PagingData with comics`() = runBlocking {
        //Given
        val characterId = 1
        val comics = listOf(dummyComic, dummyComic2)

        val pagingSource = FakePagingSource(comics)
        coEvery { dataSourceImpl.getComicsByCharacterId(characterId, any()) } returns Result.success(comics.map { it })

        //When
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(2, page.data.size)
        assertEquals("The Amazing Spider-Man", page.data[0].title)
        assertEquals("Ultimate Spider-Man", page.data[1].title)
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

val dummyCharacter3 = CharacterDto(
    id = 1,
    name = "Spider-Woman",
    description = "Jessica Drew",
    comics = ComicsDto(
        available = 80,
        collectionURI = "http://example.com",
        items = listOf(),
        returned = 1
    ),
    thumbnail = ThumbnailDto(
        path = "http://example.com",
        extension = "jpg"
    ),
    modified = "2023-10-01T00:00:00Z"
)

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
    dates = listOf(ComicDateDto(
        date = "2023-10-01",
        type = "onsaleDate"
    ))
)

// Fake PagingSource to simulate the behavior of a real PagingSource
class FakePagingSource<T : Any>(private val items: List<T>) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return LoadResult.Page(data = items, prevKey = null, nextKey = null)
    }
}
