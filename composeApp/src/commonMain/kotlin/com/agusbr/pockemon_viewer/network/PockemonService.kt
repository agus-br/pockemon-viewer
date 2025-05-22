package com.agusbr.pockemon_viewer.network

import com.agusbr.pockemon_viewer.model.Pokemon
import com.agusbr.pockemon_viewer.model.PokemonListItem
import com.agusbr.pockemon_viewer.model.PokemonListResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object PokemonApiService {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignora campos que no están en tus modelos
            })
        }
    }

    suspend fun fetchPokemonList(limit: Int = 20, offset: Int = 0): List<PokemonListItem> {
        return httpClient
            .get("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$offset")
            .body<PokemonListResponse>()
            .results
    }

    suspend fun fetchPokemonDetails(url: String): Pokemon {
        return httpClient.get(url).body()
    }
}
