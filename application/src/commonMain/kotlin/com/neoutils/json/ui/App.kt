package com.neoutils.json.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun App() = MaterialTheme {


    val code = remember { mutableStateOf("") }

    CodeEditor(
        code = code.value,
        onCodeChange = {
            code.value = it
        },
        modifier = Modifier.fillMaxSize()
    )
}