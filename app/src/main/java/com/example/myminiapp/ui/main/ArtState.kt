package com.example.myminiapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myminiapp.data.ArtPieces
import com.example.myminiapp.data.ArtRepository
import androidx.compose.runtime.mutableStateOf
import com.example.myminiapp.data.Pokemon

class ArtState(private val artRepository: ArtRepository) {
    var pokemon: MutableState<Pokemon?> = mutableStateOf(null)

    var pokemonMap: MutableMap<String, Pokemon> = mutableMapOf()

    suspend fun getPokemon(pokemonName: String) {
        if (!pokemonMap.containsKey(pokemonName)) {
            val pokemon = artRepository.getPokemon(pokemonName)
            pokemonMap[pokemonName] = pokemon
        }
    }
}