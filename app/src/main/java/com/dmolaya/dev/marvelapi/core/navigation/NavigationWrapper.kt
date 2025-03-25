package com.dmolaya.dev.marvelapi.core.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dmolaya.dev.marvelapi.splash.ui.screen.SplashScreen
import com.dmolaya.dev.marvelapi.core.navigation.Route.CharactersDetail
import com.dmolaya.dev.marvelapi.core.navigation.Route.CharactersList

/*
This wrapper is used because we don't have home in the application,
otherwise we would use two different wrapper types and two different navigation graphs
*/
@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash){
        composable<Splash> {
            SplashScreen({navController.navigate(CharactersList)})
        }

        composable<CharactersList>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ){
            //TODO: Add CharacterListScreen
        }

        composable<CharactersDetail> (
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            //TODO: Add CharacterDetailScreen
        }

    }

}