package com.dmolaya.dev.marvelapi.characters.utils

import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestRetrofitInstance {
    val apiService: CharactersApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApiService::class.java)
    }
}