package com.dmolaya.dev.marvelapi.characters.ui.composables.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.ui.theme.DarkGray
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel

@Composable
fun CharactersListView(
    characters: LazyPagingItems<Character>,
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .testTag("character_list_screen_tag")
            .fillMaxSize()
            .background(DarkGray)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        columns = GridCells.Fixed(2)
    ) {
        items(characters.itemCount) { character ->
            characters[character].let { model ->
                if (model != null) {
                    CharacterItem(model) { characterId ->
                        onNavigateToDetail(characterId)
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
            .aspectRatio(0.8f)
            .testTag("character_item_tag"),
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
                text = "${character.comics.available} Comics",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    var active by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    SearchBar(
        inputField = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Buscar personaje...") },
                trailingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "search_icon")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = DarkGray,
                    focusedLabelColor = DarkGray,
                    focusedTrailingIconColor = DarkGray,
                    cursorColor = RedMarvel,
                    selectionColors = TextSelectionColors(RedMarvel, RedMarvel.copy(alpha = 0.1f)),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .testTag("character_search_tag")
                    .fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        },
        expanded = active,
        onExpandedChange = { active = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
    }
}