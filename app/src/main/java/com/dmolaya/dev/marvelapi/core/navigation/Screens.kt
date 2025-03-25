package com.dmolaya.dev.marvelapi.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

sealed interface Route {

    @Serializable
    data object CharactersList

    @Serializable
    data object CharactersDetail

}