package com.dmolaya.dev.marvelapi.characters.data.api

import com.dmolaya.dev.marvelapi.BuildConfig
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterListDto
import com.dmolaya.dev.marvelapi.core.utils.md5Hash
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApiService {

    //TODO: Add return type of all functions

    @GET("characters")
    suspend fun getCharactersByPage(
        @Query("offset") page: Int = 0,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): CharacterListDto

    @GET("characters")
    suspend fun getCharactersByName(
        @Query("nameStartsWith") name: String,
        @Query("offset") page: Int = 0,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    )

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Query("characterId") characterId: Int,
        @Query("offset") page: Int,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    ): CharacterListDto

    @GET("characters/{characterId}/comics")
    suspend fun getComicsByCharacter(
        @Query("characterId") characterId: Int,
        @Query("offset") page: Int,
        @Query("ts") ts: String = System.currentTimeMillis().toString(),
        @Query("apikey") apikey: String = BuildConfig.PUBLIC_KEY,
        @Query("hash") hash: String = md5Hash(
            System.currentTimeMillis().toString(),
            BuildConfig.PRIVATE_KEY,
            BuildConfig.PUBLIC_KEY
        )
    )

}