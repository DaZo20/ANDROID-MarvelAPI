package com.dmolaya.dev.marvelapi.characters.data.api

import com.dmolaya.dev.marvelapi.BuildConfig
import com.dmolaya.dev.marvelapi.characters.data.model.ApiResponse
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDto
import com.dmolaya.dev.marvelapi.characters.data.model.DataContainer
import com.dmolaya.dev.marvelapi.core.utils.md5Hash
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApiService {

    @GET("characters")
    suspend fun getCharactersByPage(
        @Query("offset") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): ApiResponse<CharacterDto>

    @GET("characters")
    suspend fun getCharactersByName(
        @Query("nameStartsWith") name: String,
        @Query("offset") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): ApiResponse<CharacterDto>

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): ApiResponse<CharacterDto>

    @GET("characters/{characterId}/comics")
    suspend fun getComicsByCharacter(
        @Path("characterId") characterId: Int,
        @Query("offset") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): ApiResponse<ComicDto>

}