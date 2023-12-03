package com.example.myminiapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myminiapp.data.ArtPieces
import com.example.myminiapp.data.ArtRepository

class ArtState(private val artRepository: ArtRepository) {

    var artwork: MutableState<List<ArtPieces>> = mutableStateOf(emptyList())

    suspend fun getArtwork() {
        artwork.value = artRepository.getArtwork().pieces
    }

    fun getImageUrl(url: String): String {
        return artRepository.getImageUrl(url)
    }
}
