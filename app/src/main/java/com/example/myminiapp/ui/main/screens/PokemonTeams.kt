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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myminiapp.ui.main.app.MyBottomAppBar
import com.example.myminiapp.ui.main.app.MyTopAppBar
import kotlinx.coroutines.launch

@Composable
fun PokemonTeams(
    savedTeams: List<List<String>>,
    showTeams: Boolean, // Added parameter to control the title dynamically
    navigateBack: () -> Unit // Function to navigate back
) {

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
                title = "My Saved Pokemon Teams", // Set the title for PokemonTeams screen
                showDetails = false, // Adjust as per your logic
                onBackClick = { navigateBack() } // Call navigateBack function on back click
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {

                // Display each saved team as a separate item
                savedTeams.forEachIndexed { index, team ->
                    Text(
                        text = "Team ${index + 1}: $team",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Use Spacer with weight to fill remaining space
                Spacer(modifier = Modifier.weight(1f))
            }

            // Add a Spacer with a minimum height to maintain a gap between content and BottomAppBar
            Spacer(modifier = Modifier.height(16.dp))

            // Add MyBottomAppBar
            MyBottomAppBar(
                onHomeClick = { /* Handle Home Click */ },
                onRefreshClick = { /* Handle Refresh Click */ },
                onTeamsClick = { /* Handle Teams Click */ }
            )
        }
    }
}