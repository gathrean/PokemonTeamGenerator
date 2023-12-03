package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import coil.compose.AsyncImage
import com.example.myminiapp.ui.main.PokemonState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            val artState = remember { PokemonState(artRepository) }

            LaunchedEffect(
                key1 = artState,
                block = {
                    artState.getPokemon("pikachu")
                })

            MainContent(artState)
        }
    }
}

@Composable
fun MainContent(artState: PokemonState) {
    val randomPokemonNames = remember { mutableStateListOf<String>() }
    var selectedPokemon by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = artState) {
        repeat(9) {
            val randomPokemon = artState.getRandomPokemon()
            randomPokemonNames.add(randomPokemon.name)
            artState.getPokemon(randomPokemon.name)
        }
    }

    if (selectedPokemon == null) {
        PokemonList(artState, randomPokemonNames) { pokemonName ->
            selectedPokemon = pokemonName // Update the selectedPokemon when an item is clicked
        }
    } else {
        PokemonDetails(artState, selectedPokemon)
    }
}

@Composable
fun PokemonList(
    artState: PokemonState,
    randomPokemonNames: List<String>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(randomPokemonNames.size) { index ->
            val pokemonName = randomPokemonNames[index]
            val pokemon = artState.pokemonMap[pokemonName]

            if (pokemon != null) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onItemClick(pokemonName) // Pass the clicked Pokemon's name
                        }
                ) {
                    // Display Pokemon sprite instead of the name
                    pokemon.images.frontDefault?.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    // You can display other brief details if needed...
                }
            } else {
                Text("Loading...")
            }
        }
    }
}

@Composable
fun PokemonDetails(artState: PokemonState, pokemonName: String?) {
    val selectedPokemon = pokemonName?.let { artState.pokemonMap[it] }

    selectedPokemon?.let { pokemon ->
        Column {
            Text("Name: ${pokemon.name}")
            AsyncImage(
                model = pokemon.images.frontDefault,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            // Display other detailed information...
            Text("ID: ${pokemon.id}")
            Text("Height: ${pokemon.height}")
            Text("Weight: ${pokemon.weight}")
            Text("Abilities: ${pokemon.abilities.joinToString { it.ability.name }}")
            Text("Moves: ${pokemon.moves.joinToString { it.move.name }}")
            Text("Stats: ${pokemon.stats.joinToString { "${it.stat.name}: ${it.baseStat}" }}")
            Text("Types: ${pokemon.types.joinToString { it.type.name }}")
        }
    } ?: Text("Loading...")
}
