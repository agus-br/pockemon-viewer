package com.agusbr.pockemon_viewer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.agusbr.pockemon_viewer.ui.screens.PokemonScreen

@Composable
fun App() {
    MaterialTheme {
        PokemonScreen()
    }
}