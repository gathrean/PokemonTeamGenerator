package com.example.myminiapp.ui.main.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (showBackButton) { // Only show the back button if we want it
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}