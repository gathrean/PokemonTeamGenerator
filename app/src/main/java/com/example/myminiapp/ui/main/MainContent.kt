package com.example.myminiapp.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MainContent(
    artState: PokemonState,
    randomPokemonNames: List<String>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(randomPokemonNames.size) { index ->
            val pokemonName = randomPokemonNames[index]
            val pokemon = artState.pokemonMap[pokemonName]

            PokemonBox(pokemon, onItemClick)
        }
    }
}