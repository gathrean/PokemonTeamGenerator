package com.example.myminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myminiapp.ui.main.screens.PokemonApp

/**
 * Gathrean Dela Cruz
 * A01167248
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artRepository = (application as MyApp).artRepository

        setContent {
            PokemonApp(artRepository)
        }
    }
}