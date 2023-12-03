package com.example.myminiapp.data

import com.google.gson.annotations.SerializedName

data class Art(
    @SerializedName("data")
    val pieces: List<ArtPieces>
)

data class ArtPieces(
    val id: String,
    val title: String,
    @SerializedName("image_id")
    val image: String
)