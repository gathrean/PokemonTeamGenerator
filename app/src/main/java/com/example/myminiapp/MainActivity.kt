package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.lifecycle.lifecycleScope
import com.example.myminiapp.data.PokemonRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            PokemonApp(artRepository)
        }
    }
}

// Function to generate a new random list of Pokémon names
suspend fun generateRandomPokemonNames(artState: PokemonState): List<String> {
    return (1..6).map {
        val randomPokemon = artState.getRandomPokemon()
        artState.getPokemon(randomPokemon.name)
        randomPokemon.name
    }
}

@Composable
fun PokemonApp(
    artRepository: PokemonRepository
) {
    val artState = remember { PokemonState(artRepository) }
    var selectedPokemon by remember { mutableStateOf<String?>(null) }
    var randomPokemonNames by remember { mutableStateOf<List<String>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = artState) {
        randomPokemonNames = (1..6).map {
            val randomPokemon = artState.getRandomPokemon()
            artState.getPokemon(randomPokemon.name)
            randomPokemon.name
        }
    }
    Box(
        modifier = Modifier
            .background(Color(0xFF304dd2)) // Change to the desired color
    ) {
        Column {
            // Add a button to generate a new random list of Pokémon
            Button(
                onClick = {
                    // Use coroutineScope to launch a coroutine
                    coroutineScope.launch {
                        randomPokemonNames = generateRandomPokemonNames(artState)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                        Text(
                            text = "GENERATE RANDOM TEAM",
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            )

            if (selectedPokemon == null) {
                MainContent(artState, randomPokemonNames) { pokemonName ->
                    selectedPokemon =
                        pokemonName // Update selectedPokemon when an item is clicked
                }
            } else {
                PokemonDetails(artState, selectedPokemon) {
                    selectedPokemon =
                        null // Reset selectedPokemon when back button is clicked
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
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(randomPokemonNames.size) { index ->
            val pokemonName = randomPokemonNames[index]
            val pokemon = artState.pokemonMap[pokemonName]

            if (pokemon != null) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            onItemClick(pokemonName) // Pass the clicked Pokemon's name
                        }
                        .background(Color(0xFFf5f5ee))
                        .height(200.dp)
                ) {
                    pokemon.images.frontDefault.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(200.dp),
                        )
                    }
                }
                Text(
                    text = pokemon.name
                        .split("-")
                        .joinToString(" ") { it ->
                            it.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                        },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, // Set the text to bold
                    color = Color(0xFFf9f6ff), // Set the text color to white
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFF1c1e20)) // Set the background color to black
                )

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

            AsyncImage(
                model = pokemon.images.frontDefault,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Column {
                Text(
                    text = pokemon.name
                        .split("-")
                        .joinToString(" ") { it ->
                            it.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                        },
                    fontSize = 50.sp,
                    color = Color.White // Changing Pokemon Name Color to White
                )

                Column(
                    modifier = Modifier
                        .background(Color(0xFFf9f6ff)) // Changing Background Color to Black
                        .padding(8.dp)
                ) {
                    listOf(
                        "ID: ${pokemon.id}",
                        "Type(s): ${pokemon.types.joinToString { it.type.name.uppercase() }}",
                        "Height: ${pokemon.height}",
                        "Weight: ${pokemon.weight}",
                        "Abilities: ${pokemon.abilities.joinToString { it.ability.name }}",
                        buildString {
                            append("Stats:")
                            pokemon.stats.forEach {
                                appendLine()
                                append("\u2022 ${it.stat.name.uppercase()}: ${it.baseStat}")
                            }
                        },
                    ).forEach {
                        Text(it, fontSize = 18.sp)
                    }
                }

            }
        }
    } ?: Text("Loading...")
}
