package com.example.myminiapp.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PokemonRepository(private val client: HttpClient) {

    suspend fun getRandomPokemon(offset: Int): Pokemon {
        val response = client.get("${ApiEndPoints.POKEMON.url}?offset=$offset&limit=1")
        val pokemonList = Gson().fromJson(response.body<JsonObject>().getAsJsonArray("results"), Array<Pokemon>::class.java)
        return pokemonList.first()
    }

    suspend fun getPokemon(pokemonName: String): Pokemon {
        val response = client.get("${ApiEndPoints.POKEMON.url}/$pokemonName")
        return deserializeJson(response.body<JsonObject>().toString())
    }

    private fun deserializeJson(json: String): Pokemon {
        return Gson().fromJson(json, Pokemon::class.java)
    }
}