package com.agusbr.pockemon_viewer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agusbr.pockemon_viewer.model.Pokemon
import com.agusbr.pockemon_viewer.network.PokemonApiService
import kotlinx.coroutines.launch
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import androidx.compose.runtime.remember
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.*

@Composable
fun PokemonScreen() {
    val scope = rememberCoroutineScope()
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(0) }
    val limit = 20

    fun loadMorePokemon() {
        if (isLoadingMore) return
        isLoadingMore = true

        scope.launch {
            val api = PokemonApiService
            val items = api.fetchPokemonList(limit = limit, offset = offset)
            val newPokemons = items.map { api.fetchPokemonDetails(it.url) }

            pokemons = pokemons + newPokemons
            offset += limit
            isLoadingMore = false
        }
    }

    LaunchedEffect(Unit) {
        loadMorePokemon()
        isLoading = false
    }

    if (isLoading && pokemons.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp), // Se adapta al tamaño de la pantalla
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(pokemons.size) { index ->
                PokemonCard(pokemon = pokemons[index])

                // Scroll infinito
                if (index == pokemons.size - 1 && !isLoadingMore) {
                    loadMorePokemon()
                }
            }

            if (isLoadingMore) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}


@Composable
fun PokemonCard(pokemon: Pokemon) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            KamelImage(
                resource = asyncPainterResource(pokemon.sprites.frontDefault),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
                onLoading = {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                },
                onFailure = {
                    Text("⚠️", Modifier.align(Alignment.Center))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${pokemon.name.replaceFirstChar { it.uppercase() }} (#${pokemon.id})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pokemon.abilities.size) { index ->
                    AbilityChip(pokemon.abilities[index].ability.name)
                }
            }
        }
    }
}

@Composable
fun AbilityChip(ability: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        tonalElevation = 2.dp
    ) {
        Text(
            text = ability.replaceFirstChar { it.uppercase() },
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
