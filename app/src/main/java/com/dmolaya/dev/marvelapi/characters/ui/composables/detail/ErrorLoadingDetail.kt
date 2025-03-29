package com.dmolaya.dev.marvelapi.characters.ui.composables.detail

import androidx.compose.foundation.layout.Arrangement
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
import com.dmolaya.dev.marvelapi.characters.ui.viewmodel.CharacterDetailViewModel
import com.dmolaya.dev.marvelapi.ui.theme.RedMarvel

@Composable
fun ErrorLoadingDetail(characterId: Int, characterDetailViewModel: CharacterDetailViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_loading_character),
            color = RedMarvel,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*add load function*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = RedMarvel
            )
        ) {
            Text(text = stringResource(R.string.retry_text_button), color = Color.White)
        }
    }
}