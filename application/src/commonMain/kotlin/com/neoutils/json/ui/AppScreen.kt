package com.neoutils.json.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class AppScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { AppViewModel() }

        val textField by viewModel.textField.collectAsState()

        CodeEditor(
            value = textField,
            onValueChange = {
                viewModel.onValueChange(it)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}