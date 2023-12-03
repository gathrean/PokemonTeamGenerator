package com.example.myminiapp.data

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    val types: List<PokemonType>,
    @SerializedName("sprites")
    val images: PokemonImages
)

data class PokemonType(
    val slot: Int,
    val type: TypeDetails
)

data class TypeDetails(
    val name: String
)

data class PokemonImages(
    @SerializedName("front_default")
    val frontDefault: String
)
