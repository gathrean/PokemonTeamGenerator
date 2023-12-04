package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myminiapp.ui.main.app.MyBottomAppBar
import com.example.myminiapp.ui.main.app.MyTopAppBar
import kotlinx.coroutines.launch

/**
 * PokemonTeams is a composable that displays a list of saved Pokemon teams.

 */
@Composable
fun PokemonTeams(
    savedTeams: List<List<String>>,
    navigateBack: () -> Unit,
    showStartScreen: Boolean, // Flag from PokemonApp
    showHome: Boolean, // Flag from PokemonApp
    showSavedTeamsList: Boolean // Flag from PokemonApp
) {
// Using a simple Boolean state to control which screen to display
    var showStartScreenInternal by remember { mutableStateOf(showStartScreen) }

    if (showStartScreenInternal) {
        // Redirecting to the StartScreen when the flag is true
        StartScreen(
            onGenerateClick = {
                // Handle click action here if needed
                showStartScreenInternal = false // Set the flag to switch back to PokemonTeams
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
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MyTopAppBar(
                    "My Saved Pokemon Teams",
                    true,
                    onBackClick = { navigateBack() } // Adding back navigation here
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {

                    savedTeams.forEachIndexed { index, team ->
                        Text(
                            text = "Team ${index + 1}: $team",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
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