package com.example.myminiapp.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonsForGeneratePage(
    onGenerateClick: () -> Unit,
    onSaveTeamClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onGenerateClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Re-Generate ",
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier.size(25.dp)
            )
        }

        Button(
            onClick = onSaveTeamClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Save Team ",
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Save",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}