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
    selectedPokemonName: String?,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            selectedPokemonName?.let { name ->
                Text(text = formatPokemonName(name))
            }
        },
        navigationIcon = {
            if (showBackButton) {
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

fun formatPokemonName(name: String): String {
    return name.split("-").joinToString(" ") { it.capitalize() }
}
