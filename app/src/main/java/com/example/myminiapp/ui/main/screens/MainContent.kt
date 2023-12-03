package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.myminiapp.ui.main.app.PokemonBox
import com.example.myminiapp.ui.main.state.PokemonState

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