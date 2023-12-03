package com.example.myminiapp.ui.main.state

import com.example.myminiapp.data.PokemonRepository
import com.example.myminiapp.data.Pokemon

class PokemonState(private val artRepository: PokemonRepository) {

    var pokemonMap: MutableMap<String, Pokemon> = mutableMapOf()

    suspend fun getRandomPokemon(): Pokemon {
        val randomOffset = (0..1118).random() // Assuming there are 1118 Pokemon
        return artRepository.getRandomPokemon(randomOffset)
    }


    suspend fun getPokemon(pokemonName: String) {
        if (!pokemonMap.containsKey(pokemonName)) {
            val pokemon = artRepository.getPokemon(pokemonName)
            pokemonMap[pokemonName] = pokemon
        }
    }
}