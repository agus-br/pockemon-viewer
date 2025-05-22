package com.agusbr.pockemon_viewer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val abilities: List<PokemonAbility>
)

@Serializable
data class Sprites(
    @SerialName("front_default") val frontDefault: String
)

@Serializable
data class PokemonAbility(
    val ability: Ability
)

@Serializable
data class Ability(
    val name: String
)

@Serializable
data class PokemonListResponse(
    val results: List<PokemonListItem>
)

@Serializable
data class PokemonListItem(
    val name: String,
    val url: String
)
