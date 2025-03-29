package com.dmolaya.dev.marvelapi.characters.data.datasource

import com.dmolaya.dev.marvelapi.characters.data.api.CharactersApiService
import com.dmolaya.dev.marvelapi.characters.data.model.CharacterDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicDto
import com.dmolaya.dev.marvelapi.characters.data.model.ComicsDto

interface CharactersDataSource {

    interface Remote {
        suspend fun getCharacterById(id: Int): Result<CharacterDto?>
        suspend fun getCharactersByPage(page: Int): Result<List<CharacterDto?>>
        suspend fun getCharactersByName(name: String, page: Int): Result<List<CharacterDto?>>
        suspend fun getComicsByCharacterId(characterId: Int, page: Int): Result<List<ComicDto?>>

    }
}

class CharactersDataSourceImpl(
    private val charactersApiService: CharactersApiService
) : CharactersDataSource.Remote {

    override suspend fun getCharacterById(id: Int): Result<CharacterDto?> {
        return try {
            val response = charactersApiService.getCharacterById(id)
            Result.success(response.data.results.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharactersByPage(page: Int): Result<List<CharacterDto?>> {
        return try {
            val response = charactersApiService.getCharactersByPage(page)
            Result.success(response.data.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharactersByName(name: String, page: Int): Result<List<CharacterDto?>> {
        return try {
            val response = charactersApiService.getCharactersByName(name, page)
            Result.success(response.data.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComicsByCharacterId(characterId: Int, page: Int): Result<List<ComicDto?>> {
        return try {
            val response = charactersApiService.getComicsByCharacter(characterId, page)
            Result.success(response.data.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}