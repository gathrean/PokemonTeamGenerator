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
    var selectedPokemonName by remember { mutableStateOf<String?>(null) }
    var showDetails by remember { mutableStateOf(false) }
    var randomPokemonNames by remember { mutableStateOf<List<String>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Navigation and UI state flags
    var showHome by remember { mutableStateOf(true) }
    var showSavedTeamsList by remember { mutableStateOf(false) }
    val savedTeams = remember { mutableStateListOf<List<String>>() }
    val showTeams by remember { mutableStateOf(false) }

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
            showHome -> {
                HomeScreen(
                    onGenerateClick = { showHome = false },
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
                    showTeams = showTeams,
                    navigateBack = { showSavedTeamsList = false }
                )
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyTopAppBar(
                            title = "PokéTeam Generator",
                            onBackClick = { showDetails = false }
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

                        if (!showDetails) {
                            if (randomPokemonNames == null || isLoading) {
                                LoadingCircle()
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
                        onHelpClick = {},
                        onHomeClick = { showHome = true },
                        onSavedClick = { showSavedTeamsList = true }
//                    onRefreshClick = {
//                        coroutineScope.launch {
//                            isLoading = true // Set loading to true before refreshing
//                            randomPokemonNames = generateRandomPokemonNames()
//                            isLoading = false // Set loading to false after refreshing
//                        }
//                    },
//                    onHomeClick = {
//                        showHome = true
//                    },
//                    onTeamsClick = {
//                        // Handle info click (go back to previously selected Pokemon details)
//                        showSavedTeamsList = true // Set the flag to display saved teams list
//                    }
                    )
                }
            }
        }
    }
}