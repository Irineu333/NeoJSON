package com.neoutils.json.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Desktop Example",
        state = rememberWindowState(
            position = WindowPosition.Aligned(
                Alignment.Center
            )
        )
    ) {
        AppRoute()
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppRoute()
}