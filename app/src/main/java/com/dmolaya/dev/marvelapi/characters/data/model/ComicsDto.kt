package com.dmolaya.dev.marvelapi.characters.data.model

import com.google.gson.annotations.SerializedName

data class ComicsDto(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<ItemDto>,
    @SerializedName("returned") val returned: Int
)
