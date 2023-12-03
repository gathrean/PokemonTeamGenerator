package com.example.myminiapp.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyBottomAppBar(
    onHomeClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onInfoClick: () -> Unit
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

            // HOME PAGE
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onHomeClick() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = "Home",
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }

            // GENERATE NEW TEAMS
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onRefreshClick() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = "Generate",
                    modifier = Modifier.offset(y = (-5).dp)
                )
            }


            // VIEW TEAMS
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onInfoClick() }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
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