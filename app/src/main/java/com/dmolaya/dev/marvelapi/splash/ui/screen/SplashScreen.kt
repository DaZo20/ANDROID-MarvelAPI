package com.dmolaya.dev.marvelapi.splash.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.dmolaya.dev.marvelapi.R
import com.dmolaya.dev.marvelapi.splash.ui.viewmodel.SplashScreenViewModel
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel

@Composable
fun SplashScreen(onNavigateToCharacters: () -> Unit, splashScreenViewModel: SplashScreenViewModel = hiltViewModel()) {
    val navigateToCharacters by splashScreenViewModel.navigateToCharacters.collectAsState()
    var alpha by remember { mutableFloatStateOf(0f) }
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(Unit) { alpha = 1f }
    LaunchedEffect(navigateToCharacters) {
        if (navigateToCharacters) {
            onNavigateToCharacters()
        }
    }

    ConstraintLayout(Modifier
        .fillMaxSize()
        .background(RedMarvel)) {
        val (img) = createRefs()
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .alpha(animatedAlpha) //Check with graphicsLayer
                .constrainAs(img) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            })
    }
}