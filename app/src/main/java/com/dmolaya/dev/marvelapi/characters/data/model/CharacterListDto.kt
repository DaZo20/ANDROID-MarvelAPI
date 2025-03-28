package com.dmolaya.dev.marvelapi.characters.data.model

import com.google.gson.annotations.SerializedName

data class CharacterListDto(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: ResultDto,
)
