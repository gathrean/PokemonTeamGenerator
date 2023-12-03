package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import coil.compose.AsyncImage
import com.example.myminiapp.ui.main.ArtState

/**
 * Name: Gathrean Dela Cruz
 * Student ID: A01167248
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            val artState = ArtState(artRepository)

            LaunchedEffect(
                key1 = artState,
                block = {
                    artState.getArtwork()
                })

            MainContent(artState)
        }
    }
}

@Composable
fun MainContent(artState: ArtState) {
    LazyColumn(content = {
        items(artState.artwork.value.size) {
            Text(artState.artwork.value[it].title)
            AsyncImage(
                model = artState.getImageUrl(artState.artwork.value[it].image),
                contentDescription = null
            )
        }
    })
}
