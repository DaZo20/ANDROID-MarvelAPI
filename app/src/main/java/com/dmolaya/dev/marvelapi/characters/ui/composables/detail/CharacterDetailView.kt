package com.dmolaya.dev.marvelapi.characters.ui.composables.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dmolaya.dev.marvelapi.R
import com.dmolaya.dev.marvelapi.characters.domain.model.Character
import com.dmolaya.dev.marvelapi.characters.domain.model.Comic
import com.dmolaya.dev.marvelapi.ui.theme.DarkGray

@Composable
fun CharacterDetailView(
    character: Character,
    comicList: LazyPagingItems<Comic>
) {
    Scaffold { paddingValues ->

        ConstraintLayout(
            modifier = Modifier
                .background(DarkGray)
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            val (imgBack, backAlpha, gradient, img, name, descriptionHeader, description, comicsHeader, comics) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(character.thumbnail),
                contentDescription = "blur_image_background",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = 450.dp)
                    .constrainAs(imgBack) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )
            Box(
                modifier = Modifier
                    .heightIn(max = 450.dp)
                    .background(color = DarkGray.copy(alpha = 0.8f))
                    .fillMaxSize()
                    .constrainAs(backAlpha) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.thumbnail)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "character_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .testTag("character_detail_image_tag")
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(10.dp))
                    .constrainAs(img) {
                        bottom.linkTo(imgBack.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .constrainAs(gradient) {
                        bottom.linkTo(img.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, DarkGray),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Text(
                text = character.name,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .testTag("character_detail_name_tag")
                    .constrainAs(name) {
                        bottom.linkTo(img.bottom, margin = 38.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
            )

            Text(
                text = stringResource(R.string.description_label),
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .constrainAs(descriptionHeader) {
                        top.linkTo(img.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = character.description,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .testTag("character_detail_description_tag")
                    .heightIn(min = 100.dp)
                    .constrainAs(description) {
                        top.linkTo(descriptionHeader.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = stringResource(R.string.comic_label),
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .constrainAs(comicsHeader) {
                        top.linkTo(description.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            LazyRow(
                modifier = Modifier
                    .constrainAs(comics) {
                        top.linkTo(comicsHeader.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                items(comicList.itemCount) { comics ->
                    comicList[comics]?.let { ComicCharacterItem(it) }
                }
            }

        }
    }

}

@Composable
fun ComicCharacterItem(comic: Comic) {
    Card(
        modifier = Modifier
            .testTag("character_detail_comic_tag")
            .fillMaxWidth()
            .padding(2.dp)
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = ShapeDefaults.Large,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (img, name, comics) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(comic.thumbnail),
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
                text = comic.title,
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp)
                    .constrainAs(name) {
                        top.linkTo(img.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(comics.top)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = comic.dates?.get(0)?.date ?: "No date",
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 12.dp, bottom = 8.dp)
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
