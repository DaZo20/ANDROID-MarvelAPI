package com.dmolaya.dev.marvelapi.characters.domain.model

import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val comics: Comics,
    val thumbnail: String
){
    companion object {
        val EMPTY = Character(0, "", "", Comics.EMPTY, "")
    }
}
