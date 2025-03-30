package com.dmolaya.dev.marvelapi.characters.data.paging

import androidx.paging.PagingSource
import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.datasource.CharactersDataSourceImpl
import com.dmolaya.dev.marvelapi.characters.utils.TestRetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersPagingSourceIntegrationTest {

    private lateinit var dataSourceImpl: CharactersDataSourceImpl
    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun onBefore() {
        val apiService = TestRetrofitInstance.apiService

        dataSourceImpl = CharactersDataSourceImpl(apiService)
        charactersPagingSource = CharactersPagingSource(dataSourceImpl)
    }

    @Test
    fun `when a character page is loaded, the data must be returned correctly`() = runBlocking {

        val loadResult = charactersPagingSource.load(
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
    }
}