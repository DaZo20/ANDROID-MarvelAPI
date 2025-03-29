package com.dmolaya.dev.marvelapi.characters.domain.model

data class Comic(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val dates: List<ComicDate>? = null
)
