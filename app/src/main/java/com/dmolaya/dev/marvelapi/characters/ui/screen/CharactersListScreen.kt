package com.dmolaya.dev.marvelapi.characters.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.ui.viewmodel.CharactersViewModel
import com.dmolaya.dev.marvelapi.ui.theme.DarkGray
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel
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
            Toast.makeText(context, it.error.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold { paddingValues ->

        when {

            //Initial load
            characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
                Box(
                    modifier = Modifier
                        .background(DarkGray)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = RedMarvel
                    )
                }
            }

            //Empty state
            characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
                Box(
                    modifier = Modifier
                        .background(DarkGray)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay personajes",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            //Error state
            characters.loadState.hasError -> {
                Box(
                    modifier = Modifier
                        .background(DarkGray)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar los personajes",
                            color = RedMarvel,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { characters.retry() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RedMarvel
                            )
                        ) {
                            Text(text = "Reintentar", color = Color.White)
                        }
                    }
                }
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                        .fillMaxSize()
                        .background(DarkGray),
                    columns = GridCells.Fixed(2)
                ) {
                    items(characters.itemCount) { character ->
                        characters[character].let { model ->
                            if (model != null) {
                                CharacterItem(model) {
                                    //TODO: Navigate to character detail VIEWMODEL
                                }
                            }
                        }
                    }
                }
                if (characters.loadState.append is LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .background(DarkGray.copy(alpha = 0.8f))
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = RedMarvel
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun CharacterItem(character: Character, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .aspectRatio(0.8f),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = ShapeDefaults.Large,
        onClick = { onClick(character.id) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (img, name, comics) = createRefs()
            val imgUrl = character.thumbnail
            Image(
                painter = rememberAsyncImagePainter(imgUrl),
                contentDescription = "character_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(10))
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(name.top)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = character.name,
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(name) {
                        top.linkTo(img.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(comics.top)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = "${character.comics.items.count()} Comics",
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .constrainAs(comics) {
                        top.linkTo(name.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }

    }
}