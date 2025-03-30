package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.utils.TestRetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CharactersPagingSourceByNameIntegrationTest {

    private lateinit var dataSourceImpl: CharactersDataSourceImpl
    private lateinit var charactersPagingSourceByName: CharactersPagingSourceByName

    @Before
    fun onBefore() {
        val apiService = TestRetrofitInstance.apiService

        dataSourceImpl = CharactersDataSourceImpl(apiService)
        charactersPagingSourceByName = CharactersPagingSourceByName("Spider", dataSourceImpl)
    }

    @Test
    fun `when characters by name are loaded, the data must be returned correctly`() = runBlocking {

        val loadResult = charactersPagingSourceByName.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val pageResult = loadResult as PagingSource.LoadResult.Page
        assertTrue(pageResult.data.isNotEmpty())
        assertEquals(null, pageResult.prevKey)
        assertTrue(pageResult.nextKey != null)
        pageResult.data.forEach { character ->
            assertTrue(character.name.contains("Spider", ignoreCase = true))
        }
    }
}