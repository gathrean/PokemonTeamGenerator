package com.example.myminiapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun PokemonDetails(
    artState: PokemonState,
    pokemonName: String?
) {
    val selectedPokemon = pokemonName?.let { artState.pokemonMap[it] }

    selectedPokemon?.let { pokemon ->
        Column {

            AsyncImage(
                model = pokemon.images.frontDefault,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Column {
                Text(
                    text = pokemon.name
                        .split("-")
                        .joinToString(" ") { it ->
                            it.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                        },
                    fontSize = 50.sp,
                    color = Color.White
                )

                Column(
                    modifier = Modifier
                        .background(Color(0xFFf9f6ff))
                        .padding(8.dp)
                ) {
                    listOf(
                        "ID: ${pokemon.id}",
                        "Type(s): ${pokemon.types.joinToString { it.type.name.uppercase() }}",
                        "Height: ${pokemon.height}",
                        "Weight: ${pokemon.weight}",
                        "Abilities: ${pokemon.abilities.joinToString { it.ability.name }}",
                        buildString {
                            append("Stats:")
                            pokemon.stats.forEach {
                                appendLine()
                                append("\u2022 ${it.stat.name.uppercase()}: ${it.baseStat}")
                            }
                        },
                    ).forEach {
                        Text(it, fontSize = 18.sp)
                    }
                }

            }
        }
    } ?: Text("Loading...")
}