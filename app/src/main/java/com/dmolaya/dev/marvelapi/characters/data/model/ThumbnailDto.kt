package com.dmolaya.dev.marvelapi.characters.data.model

import com.google.gson.annotations.SerializedName

data class ThumbnailDto(
    @SerializedName("extension") val extension: String,
    @SerializedName("path") val path: String
)
