package com.agusbr.pockemon_viewer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.agusbr.pockemon_viewer.ui.screens.PokemonScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        PokemonScreen()
    }
}