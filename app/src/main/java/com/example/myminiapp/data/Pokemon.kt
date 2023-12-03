package com.example.myminiapp.data

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val moves: List<Move>,
    val species: Species,
    val stats: List<Stat>,
    val types: List<Type>,
    @SerializedName("sprites")
    val images: PokemonImages
)

data class Ability(
    val ability: AbilityDetails,
    val isHidden: Boolean,
    val slot: Int
)

data class AbilityDetails(
    val name: String,
    val url: String
)

data class Move(
    val move: MoveDetails
)

data class MoveDetails(
    val name: String,
    val url: String
)

data class Species(
    val name: String,
    val url: String
)

data class Stat(
    val baseStat: Int,
    val effort: Int,
    val stat: StatDetails
)

data class StatDetails(
    val name: String,
    val url: String
)

data class Type(
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
