package com.example.myminiapp.ui.main.state

import com.example.myminiapp.data.PokemonRepository
import com.example.myminiapp.data.Pokemon

/**
 * PokemonState is a class that holds the state of the Pokemon app.
 */
class PokemonState(private val artRepository: PokemonRepository) {

    // Map of Pokemon names to Pokemon objects
    var pokemonMap: MutableMap<String, Pokemon> = mutableMapOf()

    /// Suspend function to get a random Pokemon
    suspend fun getRandomPokemon(): Pokemon {
        val randomOffset = (0..1118).random() // Assuming there are 1118 Pokemon
        return artRepository.getRandomPokemon(randomOffset)
    }

    // Suspend function to get a Pokemon by name
    suspend fun getPokemon(pokemonName: String) {
        if (!pokemonMap.containsKey(pokemonName)) {
            val pokemon = artRepository.getPokemon(pokemonName)
            pokemonMap[pokemonName] = pokemon
        }
    }

    suspend fun getPokemonImage(pokemonName: String): String? {
        val pokemon = pokemonMap[pokemonName]
        return pokemon?.images?.frontDefault
    }

}