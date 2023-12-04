package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myminiapp.ui.main.app.MyBottomAppBar
import com.example.myminiapp.ui.main.app.MyTopAppBar
import com.example.myminiapp.ui.main.state.PokemonState
import kotlinx.coroutines.launch

/**
 * PokemonTeams is a composable that displays a list of saved Pokemon teams.

 */
@Composable
fun PokemonTeams(
    savedTeams: List<List<String>>,
    navigateBack: () -> Unit,
    showStartScreen: Boolean,
    showHome: Boolean,
    showSavedTeamsList: Boolean,
    artState: PokemonState,
    removeTeam: (Int) -> Unit // Function to remove a team by index
) {
    var showStartScreenInternal by remember { mutableStateOf(showStartScreen) }

    if (showStartScreenInternal) {
        StartScreen(
            onGenerateClick = {
                showStartScreenInternal = false
            },
            onSaveTeamClick = {
                // Handle save team action here if needed
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF304dd2))
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MyTopAppBar(
                    "My Saved Pokemon Teams",
                    true,
                    onBackClick = { navigateBack() }
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(savedTeams) { team ->
                        val teamIndex = savedTeams.indexOf(team) + 1 // Calculate team index

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize()

                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Team $teamIndex",
                                    fontSize = 30.sp,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                IconButton(
                                    onClick = {
                                        // Remove the team at a specific index, for instance, team at index 0
                                        removeTeam(teamIndex - 1)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Team"
                                    )
                                }
                            }

                            val chunkedTeam = team.chunked(3)

                            chunkedTeam.forEach { rowTeam ->
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFFE0E0E0))
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ) {
                                    rowTeam.forEach { pokemonName ->
                                        val image = remember { mutableStateOf<String?>(null) }

                                        LaunchedEffect(key1 = pokemonName) {
                                            image.value = artState.getPokemonImage(pokemonName)
                                        }

                                        image.value?.let { imageUrl ->
                                            Image(
                                                painter = rememberAsyncImagePainter(model = imageUrl),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MyBottomAppBar(
                    onStartClick = { /* use the showStartScreen flag here */ },
                    onHomeClick = { /* use the showHome flag here */ },
                    onSavedClick = { /* use the showSavedTeamsList flag here */ }
                )
            }
        }
    }
}