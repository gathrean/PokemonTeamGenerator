package com.example.myminiapp.ui.main.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * MyBottomAppBar is a composable that displays the bottom app bar.

 */
@Composable
fun MyBottomAppBar(
    onStartClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSavedClick: () -> Unit
) {
    BottomAppBar(
        contentPadding = PaddingValues(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Goes back to the Start Screen
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onStartClick() }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Start Screen",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = "Exit",
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }

            // Goes back to the page that generates the team
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onHomeClick() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = "Home",
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }


            // Goes to the page that shows the saved teams
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onSavedClick() }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "My Teams",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = "Saved",
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }

        }
    }
}