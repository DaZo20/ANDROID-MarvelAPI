package com.dmolaya.dev.marvelapi.characters.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dmolaya.dev.marvelapi.R
import com.dmolaya.dev.marvelapi.characters.ui.composables.list.CharactersListView
import com.dmolaya.dev.marvelapi.characters.ui.composables.list.CustomSearchBar
import com.dmolaya.dev.marvelapi.characters.ui.composables.list.ErrorLoadingList
import com.dmolaya.dev.marvelapi.characters.ui.viewmodel.CharactersViewModel
import com.dmolaya.dev.marvelapi.core.composables.LoadingIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CharactersListScreen(
    onNavigateToDetail: (Int) -> Unit,
    charactersViewModel: CharactersViewModel = hiltViewModel()
) {
    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val characters = charactersViewModel.characters.collectAsLazyPagingItems()
    var query by remember { mutableStateOf("") }

    BackHandler {
        if (backPressedOnce) {
            val activity = context as? Activity
            activity?.moveTaskToBack(true)
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Presiona atrás de nuevo para salir", Toast.LENGTH_SHORT).show()

            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }

    //Error handling
    LaunchedEffect(characters.loadState) {
        val errorState = characters.loadState.refresh as? LoadState.Error
            ?: characters.loadState.append as? LoadState.Error
            ?: characters.loadState.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(context, it.error.message ?: "Error desconocido", Toast.LENGTH_LONG)
                .show()
        }
    }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            charactersViewModel.getCharacterByName(query)
        } else {
            charactersViewModel.getCharactersByPage()
        }
    }

    Scaffold(
        topBar = {
            CustomSearchBar(
                query = query,
                onQueryChange = { query = it },
            )
        }
    ){ paddingValues ->

        when {

            //Initial load
            characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
                LoadingIndicator()
            }

            //Empty state
            characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
                ErrorLoadingList(
                    errorText = stringResource(R.string.empty_characters_text),
                    onClickRetry = { characters.refresh() }
                )
            }

            //Error state
            characters.loadState.hasError -> {
                ErrorLoadingList(
                    errorText = stringResource(R.string.error_loading_characters_text),
                    onClickRetry = { characters.refresh() }
                )
            }

            else -> {
                CharactersListView(
                    characters,
                    paddingValues,
                    onNavigateToDetail
                )
                if (characters.loadState.append is LoadState.Loading) {
                    LoadingIndicator()
                }
            }

        }
    }
}


