package com.dmolaya.dev.marvelapi.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dmolaya.dev.marvelapi.ui.theme.DarkGray
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .background(DarkGray.copy(alpha = 0.7f))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = RedMarvel
        )
    }
}