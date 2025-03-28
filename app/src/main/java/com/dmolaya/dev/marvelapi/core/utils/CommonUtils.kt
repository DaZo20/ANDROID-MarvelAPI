package com.dmolaya.dev.marvelapi.core.utils

import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
import com.dmolaya.dev.marvelapi.characters.data.model.ItemDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import java.security.MessageDigest
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comics
import com.dmolaya.dev.marvelapi.characters.domain.model.Item
import com.dmolaya.dev.marvelapi.characters.domain.model.Thumbnail

fun md5Hash(timestamp: String, privateKey: String, publicKey: String): String {
    val input = "$timestamp$privateKey$publicKey"
    val md5 = MessageDigest.getInstance("MD5")
    val hash = md5.digest(input.toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        comics = comics.toComics(),
        thumbnail = "${thumbnail.path}.${thumbnail.extension}".replace("http://", "https://")
    )
}

fun ThumbnailDto.toThumbnail(): Thumbnail {
    return Thumbnail(
        path = path,
        extension = extension
    )
}

fun ComicsDto.toComics(): Comics {
    return Comics(
        available = available,
        collectionURI = collectionURI,
        items = items.toItems(),
        returned = returned
    )
}

fun List<ItemDto>.toItems(): List<Item> {
    return this.map { itemDto ->
        Item(
            resourceURI = itemDto.resourceURI,
            name = itemDto.name
        )
    }
}