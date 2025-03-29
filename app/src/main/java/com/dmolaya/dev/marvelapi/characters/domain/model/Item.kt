package com.dmolaya.dev.marvelapi.characters.domain.model

data class Item(
    val name: String,
    val resourceURI: String
){
    companion object {
        val EMPTY = Item("", "")
    }
}
