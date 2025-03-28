package com.dmolaya.dev.marvelapi.characters.data.model

data class ResultDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val results: List<CharacterDto>
)
