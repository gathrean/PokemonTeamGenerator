package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            val artState = remember { PokemonState(artRepository) }
            var selectedPokemon by remember { mutableStateOf<String?>(null) }
            var randomPokemonNames by remember { mutableStateOf<List<String>>(emptyList()) }

            LaunchedEffect(key1 = artState) {
                randomPokemonNames = (1..9).map {
                    val randomPokemon = artState.getRandomPokemon()
                    artState.getPokemon(randomPokemon.name)
                    randomPokemon.name
                }
            }

            if (selectedPokemon == null) {
                MainContent(artState, randomPokemonNames) { pokemonName ->
                    selectedPokemon =
                        pokemonName // Update the selectedPokemon when an item is clicked
                }
            } else {
                PokemonDetails(artState, selectedPokemon) {
                    selectedPokemon = null // Reset selectedPokemon when back button is clicked
                }
            }
        }
    }
}

@Composable
fun MainContent(
    artState: PokemonState,
    randomPokemonNames: List<String>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
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
                    pokemon.images.frontDefault.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            } else {
                Text("Loading...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetails(
    artState: PokemonState,
    pokemonName: String?,
    onBackClicked: () -> Unit
) {
    val selectedPokemon = pokemonName?.let { artState.pokemonMap[it] }

    selectedPokemon?.let { pokemon ->
        Column {
            TopAppBar(
                title = { Text(text = "Pokemon Details") },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )

            Text("Name: ${pokemon.name}")
            AsyncImage(
                model = pokemon.images.frontDefault,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
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