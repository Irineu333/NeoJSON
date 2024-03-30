package com.neoutils.json.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun AppRoute() = MaterialTheme {
    Navigator(AppScreen())
}