package com.example.myminiapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myminiapp.data.PokemonRepository
import kotlinx.coroutines.launch

@Composable
fun PokemonApp(
    artRepository: PokemonRepository
) {
    val artState = remember { PokemonState(artRepository) }
    var selectedPokemon by remember { mutableStateOf<String?>(null) }
    var randomPokemonNames by remember { mutableStateOf<List<String>?>(null) } // Nullable list
    val coroutineScope = rememberCoroutineScope()
    var showDetails by remember { mutableStateOf(false) }
    var selectedPokemonName by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) } // Track loading state

    // Track saved teams
    val savedTeams = remember { mutableStateListOf<List<String>>() }

    // Function to handle saving a team
    fun saveTeam(team: List<String>) {
        savedTeams.add(team)
    }

    // Function to generate a new random list of Pokémon names
    suspend fun generateRandomPokemonNames(): List<String> {
        return (1..6).map {
            val randomPokemon = artState.getRandomPokemon()
            artState.getPokemon(randomPokemon.name)
            randomPokemon.name
        }
    }

    LaunchedEffect(key1 = artState) {
        isLoading = true // Set loading to true before generating
        randomPokemonNames = generateRandomPokemonNames()
        isLoading = false // Set loading to false once generation is complete
    }

    var showHome by remember { mutableStateOf(true) }
    var showSavedTeams by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF304dd2))
    ) {
        if (showHome) {
            HomeScreen(
                onGenerateClick = { showHome = false },
                onSaveTeamClick = {
                    // Call a function to save the generated team
                    randomPokemonNames?.let { team ->
                        saveTeam(team)
                    }
                }
            )
        } else if (showSavedTeams) {
            SavedTeamsList(savedTeams = savedTeams)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    MyTopAppBar(
                        title = "PokéTeam Generator",
                        showDetails = showDetails,
                        onBackClick = { showDetails = false }
                    )

// Save Team button
                    Button(
                        onClick = {
                            randomPokemonNames?.let { team ->
                                saveTeam(team)
                            }
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Save Team",
                            fontSize = 20.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Save",
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    // Button to view saved teams
                    Button(
                        onClick = {
                            showSavedTeams = true
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "View Saved Teams",
                            fontSize = 20.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Save",
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    if (!showDetails) {
                        // Display loading indicator while randomPokemonNames is null or isLoading is true
                        if (randomPokemonNames == null || isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {
                                    CircularProgressIndicator(
                                        color = Color(0xFFf9f6ff),
                                    ) // Loading indicator
                                    Text(
                                        text = "Generating...",
                                        color = Color(0xFFf9f6ff),
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                }
                            }
                        } else {
                            MainContent(artState, randomPokemonNames!!) { pokemonName ->
                                selectedPokemonName = pokemonName
                                showDetails = true
                            }
                        }
                    } else {
                        selectedPokemonName?.let { name ->
                            PokemonDetails(artState, name)
                        }
                    }
                }

                // Reserve space for the bottom bar even during loading
                if (!showDetails) {
                    Spacer(modifier = Modifier.height(25.dp))
                }

                MyBottomAppBar(
                    onRefreshClick = {
                        coroutineScope.launch {
                            isLoading = true // Set loading to true before refreshing
                            randomPokemonNames = generateRandomPokemonNames()
                            isLoading = false // Set loading to false after refreshing
                        }
                    },
                    onHomeClick = {
                        showHome = true
                    },
                    onInfoClick = {
                        // Handle info click (go back to previously selected Pokemon details)
                    }
                )
            }
        }
    }
}

@Composable
fun SavedTeamsList(savedTeams: List<List<String>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Saved Teams",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display each saved team as a separate item
        savedTeams.forEachIndexed { index, team ->
            Text(
                text = "Team ${index + 1}: $team",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}