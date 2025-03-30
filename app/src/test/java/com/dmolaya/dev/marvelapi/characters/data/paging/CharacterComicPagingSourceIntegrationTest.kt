package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.utils.TestRetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CharacterComicPagingSourceIntegrationTest {
    private lateinit var dataSourceImpl: CharactersDataSourceImpl
    private lateinit var characterComicPagingSource: CharacterComicPagingSource

    @Before
    fun onBefore() {
        val apiService = TestRetrofitInstance.apiService
        dataSourceImpl = CharactersDataSourceImpl(apiService)

        val characterId = 1011334
        characterComicPagingSource = CharacterComicPagingSource(characterId, dataSourceImpl)
    }

    @Test
    fun `when comics by character ID are loaded, the data must be returned correctly`() = runBlocking {

        val loadResult = characterComicPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,             // Página inicial
                loadSize = 20,       // Tamaño de la página
                placeholdersEnabled = false
            )
        )

        // Verify valid page
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val pageResult = loadResult as PagingSource.LoadResult.Page
        // Verify no empty data
        assertTrue(pageResult.data.isNotEmpty())
        //First page
        assertEquals(null, pageResult.prevKey)
        //Has next page
        assertTrue(pageResult.nextKey != null)
        // Verify comics
        pageResult.data.forEach { comic ->
            assertNotNull(comic.title)
            assertTrue(comic.title.isNotEmpty())
        }
    }
}