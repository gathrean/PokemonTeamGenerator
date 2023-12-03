package com.example.myminiapp.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArtRepository(private val client: HttpClient) {
    suspend fun getArtwork(): Art {
        val response = client.get(ApiEndPoints.FIELDS.url)

        val json = response.body<JsonObject>().toString()

        return deserializeJson(json)
    }

    private fun deserializeJson(json: String): Art {
        return Gson().fromJson(json, Art::class.java)
    }

    fun getImageUrl(url: String): String {
        return "https://www.artic.edu/iiif/2/$url/full/843,/0/default.jpg"
    }

}