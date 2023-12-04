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

/**
 * The main screen of the app.
 */
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
    val showHome by remember { mutableStateOf(true) }

    // Function to handle saving a team
    fun saveTeam(team: List<String>) {
        savedTeams.add(team)
    }

    // Function to handle removing a team
    fun removeTeam(teamIndex: Int) {
        savedTeams.removeAt(teamIndex)
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
            showStartScreen -> { // Start screen logic
                StartScreen(
                    onGenerateClick = { showStartScreen = false },
                    onSaveTeamClick = {
                        randomPokemonNames?.let { team ->
                            saveTeam(team)
                        }
                    }
                )
            }

            showSavedTeamsList -> { // Show the list of saved teams
                PokemonTeams(
                    savedTeams = savedTeams,
                    navigateBack = { showSavedTeamsList = false },
                    showStartScreen = showStartScreen,
                    showHome = showHome,
                    showSavedTeamsList = showSavedTeamsList,
                    artState = artState,
                    removeTeam = { teamIndex ->
                        removeTeam(teamIndex)
                    }
                )
            }

            showDetails -> { // Show the details of a selected Pokémon
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Show the top bar with the name of the selected Pokémon
                        MyTopAppBar(
                            "$selectedPokemonName Details",
                            true,
                            onBackClick = {
                                showDetails = false
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Show the details of the selected Pokémon
                        selectedPokemonName?.let { name ->
                            PokemonDetails(artState, name)
                        }

                    }

                    // Show the bottom bar
                    MyBottomAppBar(
                        onStartClick = { showStartScreen = true },
                        onHomeClick = { },
                        onSavedClick = { showSavedTeamsList = true }
                    )
                }
            }

            else -> {
                // Default screen
                // Which just shows the top bar, buttons, 6 random Pokémon, and bottom bar

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Show the top bar with the app title
                        MyTopAppBar(
                            "PokéTeam Generator",
                            false,
                            onBackClick = {
                                showDetails = false
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Row to display the Save Team and View Teams buttons
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

                        // Display loading indicator while randomPokemonNames is null or isLoading is true
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

                    // Show the bottom bar
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