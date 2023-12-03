package com.example.myminiapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myminiapp.data.Pokemon
import java.util.Locale

@Composable
fun PokemonBox(
    pokemon: Pokemon?,
    onItemClick: (String) -> Unit
) {
    if (pokemon != null) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    onItemClick(pokemon.name) // Pass the clicked Pokemon's name
                }
                .background(Color(0xFFf5f5ee))
                .height(200.dp)
        ) {
            pokemon.images.frontDefault.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
            }
        }
        Text(
            text = pokemon.name
                .split("-")
                .joinToString(" ") { it ->
                    it.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFf9f6ff),
            modifier = Modifier
                .padding(4.dp)
                .background(Color(0xFF1c1e20))
        )

    } else {
        Text("Loading...")
    }
}