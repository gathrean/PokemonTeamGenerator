package com.example.myminiapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myminiapp.data.PokemonRepository
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp(
    artRepository: PokemonRepository
) {
    val artState = remember { PokemonState(artRepository) }
    var selectedPokemon by remember { mutableStateOf<String?>(null) }
    var randomPokemonNames by remember { mutableStateOf<List<String>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var showDetails by remember { mutableStateOf(false) }
    var selectedPokemonName by remember { mutableStateOf<String?>(null) }

    // Function to generate a new random list of Pokémon names
    suspend fun generateRandomPokemonNames(): List<String> {
        return (1..6).map {
            val randomPokemon = artState.getRandomPokemon()
            artState.getPokemon(randomPokemon.name)
            randomPokemon.name
        }
    }

    LaunchedEffect(key1 = artState) {
        randomPokemonNames = generateRandomPokemonNames()
    }

    Box(
        modifier = Modifier
            .background(Color(0xFF304dd2))
    ) {
        Column {
            MyTopBar(
                title = "PokéTeam Builder",
                showDetails = showDetails,
                onBackClick = { showDetails = false }
            )

            if (!showDetails) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            randomPokemonNames = generateRandomPokemonNames()
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
            }

            if (!showDetails) {
                MainContent(artState, randomPokemonNames) { pokemonName ->
                    selectedPokemonName = pokemonName
                    showDetails = true
                }
            } else {
                selectedPokemonName?.let { name ->
                    PokemonDetails(artState, name)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(title: String, showDetails: Boolean, onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = if (showDetails) "Pokemon Details" else title) },
        navigationIcon = {
            if (showDetails) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}