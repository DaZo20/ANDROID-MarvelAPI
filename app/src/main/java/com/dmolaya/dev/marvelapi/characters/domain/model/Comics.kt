package com.dmolaya.dev.marvelapi.characters.domain.model



data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
){
    companion object {
        val EMPTY = Comics(0, "", listOf(Item.EMPTY), 0)
    }
}
