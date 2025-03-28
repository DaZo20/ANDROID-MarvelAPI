package com.dmolaya.dev.marvelapi.characters.data.model

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("name") val name: String,
    @SerializedName("resourceURI") val resourceURI: String
)
