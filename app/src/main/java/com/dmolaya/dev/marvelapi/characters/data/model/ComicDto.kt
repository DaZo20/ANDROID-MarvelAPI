package com.dmolaya.dev.marvelapi.characters.data.model

data class ComicDto(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailDto,
    val dates: List<ComicDateDto>
)
