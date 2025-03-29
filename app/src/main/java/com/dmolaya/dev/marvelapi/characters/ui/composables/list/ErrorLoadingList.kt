package com.dmolaya.dev.marvelapi.characters.ui.composables.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmolaya.dev.marvelapi.R
import com.dmolaya.dev.marvelapi.ui.theme.DarkGray
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel

@Composable
fun ErrorLoadingList(errorText: String, onClickRetry: () -> Unit ){
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
                text = errorText,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onClickRetry() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedMarvel
                )
            ) {
                Text(text = stringResource(R.string.retry_text_button), color = Color.White)
            }
        }
    }
}