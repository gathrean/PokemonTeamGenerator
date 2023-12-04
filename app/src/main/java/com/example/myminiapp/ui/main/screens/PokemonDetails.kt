package com.example.myminiapp.ui.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myminiapp.ui.main.state.PokemonState
import java.util.Locale

/**
 * PokemonDetails is a composable that displays a Pokemon's details.

 */
@Composable
fun PokemonDetails(
    artState: PokemonState,
    pokemonName: String?
) {
    val selectedPokemon = pokemonName?.let { artState.pokemonMap[it] }

    selectedPokemon?.let { pokemon ->
        Box(
            modifier = Modifier
                .background(Color(0xFFf5f5ee))
                .padding(18.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Horizontal gap from left and right borders
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth() // Column spans the available width
            ) {

                // Pokemon image
                AsyncImage(
                    model = pokemon.images.frontDefault,
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )

                Spacer(modifier = Modifier.height(12.dp)) // Vertical gap

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp) // Padding for content within the column
                ) {

                    // Pokemon name
                    Text(
                        text = pokemon.name
                            .split("-")
                            .joinToString(" ") { it ->
                                it.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                            },
                        fontSize = 50.sp,
                    )

                    // Description
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        listOf(
                            "ID: ${pokemon.id}",
                            "Type(s): ${pokemon.types.joinToString { it.type.name.uppercase() }}",
                            "Height: ${pokemon.height}",
                            "Weight: ${pokemon.weight}",
                            "Abilities: ${pokemon.abilities.joinToString { it.ability.name }}",
                        ).forEach {
                            Text(it, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    } ?: Text("Loading...")
}