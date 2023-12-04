package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myminiapp.data.PokemonRepository
import com.example.myminiapp.ui.main.app.LoadingCircle
import com.example.myminiapp.ui.main.app.MyBottomAppBar
import com.example.myminiapp.ui.main.app.MyTopAppBar
import com.example.myminiapp.ui.main.state.PokemonState
import com.example.myminiapp.ui.main.buttons.ButtonsForGeneratePage
import kotlinx.coroutines.launch

@Composable
fun PokemonApp(
    artRepository: PokemonRepository
) {
    // Coroutine scope for asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    // Pokemon-related data and states
    val artState = remember { PokemonState(artRepository) }

    // Name of the selected Pokemon and list of random Pokémon names
    var selectedPokemonName by remember { mutableStateOf<String?>(null) }
    var randomPokemonNames by remember { mutableStateOf<List<String>?>(null) }

    // List of saved teams
    val savedTeams = remember { mutableStateListOf<List<String>>() }

    // Flag to control the current screen
    var isLoading by remember { mutableStateOf(true) }
    var showDetails by remember { mutableStateOf(false) }
    var showStartScreen by remember { mutableStateOf(true) }
    var showSavedTeamsList by remember { mutableStateOf(false) }

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

    // Generate a random team when the app is launched
    LaunchedEffect(key1 = artState) {
        isLoading = true // Set loading to true before generating
        randomPokemonNames = generateRandomPokemonNames()
        isLoading = false // Set loading to false once generation is complete
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF304dd2))
    ) {
        when {
            showStartScreen -> {
                StartScreen(
                    onGenerateClick = { showStartScreen = false },
                    onSaveTeamClick = {
                        randomPokemonNames?.let { team ->
                            saveTeam(team)
                        }
                    }
                )
            }

            showSavedTeamsList -> {
                PokemonTeams(
                    savedTeams = savedTeams,
                    navigateBack = { showSavedTeamsList = false }
                )
            }

            showDetails -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyTopAppBar(
                            "PokéTeam Generator",
                            true,
                            onBackClick = {
                                showDetails = false
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        selectedPokemonName?.let { name ->
                            PokemonDetails(artState, name)
                        }

                    }

                    MyBottomAppBar(
                        onStartClick = { showStartScreen = true },
                        onHomeClick = { },
                        onSavedClick = { showSavedTeamsList = true }
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyTopAppBar(
                            "PokéTeam Generator",
                            false,
                            onBackClick = {
                                showDetails = false
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ButtonsForGeneratePage(
                            onGenerateClick = {
                                coroutineScope.launch {
                                    isLoading = true
                                    randomPokemonNames = generateRandomPokemonNames()
                                    isLoading = false
                                }
                            },
                            onSaveTeamClick = {
                                randomPokemonNames?.let { team ->
                                    saveTeam(team)
                                }
                            }
                        )

                        if (randomPokemonNames == null || isLoading) {
                            LoadingCircle()
                        } else {
                            MainContent(artState, randomPokemonNames!!) { pokemonName ->
                                selectedPokemonName = pokemonName
                                showDetails = true
                            }
                            Spacer(modifier = Modifier.height(25.dp))
                        }

                    }

                    MyBottomAppBar(
                        onStartClick = { showStartScreen = true },
                        onHomeClick = { },
                        onSavedClick = { showSavedTeamsList = true }
                    )
                }
            }
        }
    }
}