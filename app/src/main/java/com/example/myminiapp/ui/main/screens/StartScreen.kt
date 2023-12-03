package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    onGenerateClick: () -> Unit,
    onSaveTeamClick: () -> Unit // Add a callback for Save Team click
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF304dd2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(

            text = "Welcome to PokéTeam Generator!",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                onGenerateClick()
                onGenerateClick()
                // Show the Save Team button after generating
                // Assume the team is generated and ready to be saved
                onSaveTeamClick()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Generate",
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}