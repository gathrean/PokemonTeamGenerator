package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import coil.compose.AsyncImage
import com.example.myminiapp.ui.main.PokemonState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            val artState = remember { PokemonState(artRepository) }

            LaunchedEffect(
                key1 = artState,
                block = {
                    artState.getPokemon("pikachu")
                })

            MainContent(artState)
        }
    }
}

@Composable
fun MainContent(artState: PokemonState) {
    val randomPokemonNames = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = artState) {
        repeat(10) {
            val randomPokemon = artState.getRandomPokemon()
            randomPokemonNames.add(randomPokemon.name)
            artState.getPokemon(randomPokemon.name)
        }
    }

    Column {
        randomPokemonNames.forEach { pokemonName ->
            val pokemon = artState.pokemonMap[pokemonName]

            if (pokemon != null) {
                Text("Name: ${pokemon.name}")
                AsyncImage(
                    model = pokemon.images.frontDefault,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                )
            } else {
                Text("Loading...")
            }
        }
    }
}