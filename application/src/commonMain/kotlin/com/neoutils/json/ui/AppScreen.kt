package com.neoutils.json.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class AppScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { AppViewModel() }

        val code = viewModel.code.collectAsState()

        val highlight = viewModel.highlight.collectAsState()

        CodeEditor(
            code = code.value,
            highlight = highlight.value,
            onCodeChange = {
                viewModel.onCodeChange(it)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}