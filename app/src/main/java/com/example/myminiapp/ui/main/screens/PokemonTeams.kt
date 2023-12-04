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
    showTeams: Boolean,
    navigateBack: () -> Unit
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
                title = "My Saved Pokemon Teams",
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
//                onHelpClick = {},
//                onHomeClick = { showHome = true },
//                onSavedClick = { showSavedTeamsList = true }
                onHelpClick = {},
                onHomeClick = {},
                onSavedClick = {}
            )
        }
    }
}