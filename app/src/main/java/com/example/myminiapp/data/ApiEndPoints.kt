package com.example.myminiapp.data

enum class ApiEndPoints(val url: String) {
    BASE_URL("https://pokeapi.co/api/v2"),
    POKEMON("${BASE_URL.url}/pokemon")
    // Add more endpoints if required
}