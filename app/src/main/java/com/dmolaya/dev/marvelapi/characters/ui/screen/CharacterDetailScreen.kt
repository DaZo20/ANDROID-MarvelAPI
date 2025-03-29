package com.dmolaya.dev.marvelapi.characters.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.dmolaya.dev.marvelapi.characters.ui.composables.detail.CharacterDetailView
import com.dmolaya.dev.marvelapi.characters.ui.composables.detail.ErrorLoadingDetail
import com.dmolaya.dev.marvelapi.characters.ui.viewmodel.CharacterDetailViewModel
import com.dmolaya.dev.marvelapi.core.composables.LoadingIndicator
import com.dmolaya.dev.marvelapi.core.utils.UiState

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState by characterDetailViewModel.uiState.collectAsState()
    val character by characterDetailViewModel.character.collectAsState()
    val comics = characterDetailViewModel.comics.collectAsLazyPagingItems()

    LaunchedEffect(characterId) {
        characterDetailViewModel.loadCharacterById(characterId)
    }

    DisposableEffect(true) {
        onDispose {
            characterDetailViewModel.resetUiState()
        }
    }

        when (uiState) {
            is UiState.Loading -> {
                // Show loading indicator
                LoadingIndicator()
            }

            is UiState.Success<*> -> {
                // Show character detail screen
                CharacterDetailView(character, comics)
            }

            is UiState.Error -> {
                // Show error screen
                ErrorLoadingDetail(characterId, characterDetailViewModel)
            }
        }

}
