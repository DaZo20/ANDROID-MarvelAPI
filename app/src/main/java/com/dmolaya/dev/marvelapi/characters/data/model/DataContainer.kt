package com.dmolaya.dev.marvelapi.characters.data.model

data class DataContainer<T>(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val results: List<T>
)
