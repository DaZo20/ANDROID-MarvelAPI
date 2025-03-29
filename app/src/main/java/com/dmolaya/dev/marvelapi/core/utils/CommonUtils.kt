package com.dmolaya.dev.marvelapi.core.utils

import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDateDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto
import com.dmolaya.dev.marvelapi.characters.data.model.ItemDto
import com.dmolaya.dev.marvelapi.characters.data.model.ThumbnailDto
import java.security.MessageDigest
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.characters.domain.model.ComicDate
import com.dmolaya.dev.marvelapi.characters.domain.model.Comics
import com.dmolaya.dev.marvelapi.characters.domain.model.Item
import com.dmolaya.dev.marvelapi.characters.domain.model.Thumbnail

fun md5Hash(timestamp: String, privateKey: String, publicKey: String): String {
    val input = "$timestamp$privateKey$publicKey"
    val md5 = MessageDigest.getInstance("MD5")
    val hash = md5.digest(input.toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

fun List<CharacterDto?>.toCharacterList(): List<Character> {
    return this.map { characterDto ->
        Character(
            id = characterDto?.id ?: 0,
            name = characterDto?.name ?: "",
            description = characterDto?.description?: "",
            comics = characterDto?.comics?.toComics() ?: Comics(0, "", emptyList(), 0),
            thumbnail = "${characterDto?.thumbnail?.path}.${characterDto?.thumbnail?.extension}".replace("http://", "https://")
        )
    }
}

fun List<ComicDto?>.toComicList(): List<Comic> {
    return this.map { comicDto ->
        Comic(
            id = comicDto?.id ?: 0,
            title = comicDto?.title ?: "",
            thumbnail = "${comicDto?.thumbnail?.path}.${comicDto?.thumbnail?.extension}".replace("http://", "https://"),
            dates = comicDto?.dates?.toComicDates() ?: emptyList()
        )
    }
}
fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        description = description,
        comics = comics.toComics(),
        thumbnail = "${thumbnail.path}/portrait_incredible.${thumbnail.extension}".replace("http://", "https://")
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

fun List<ComicDateDto>.toComicDates(): List<ComicDate> {
    return this.map { comicDateDto ->
        ComicDate(
            type = comicDateDto.type,
            date = comicDateDto.date
        )
    }
}