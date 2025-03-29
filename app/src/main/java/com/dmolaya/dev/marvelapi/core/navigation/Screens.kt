package com.dmolaya.dev.marvelapi.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

sealed interface Route {

    @Serializable
    object CharactersList

    @Serializable
    data class CharactersDetail(val characterId: Int)

}