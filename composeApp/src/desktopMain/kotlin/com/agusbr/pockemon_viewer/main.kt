package com.agusbr.pockemon_viewer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pockemon-viewer",
    ) {
        App()
    }
}