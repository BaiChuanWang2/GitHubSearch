package com.example.githubsearch.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    HOME(
        "Home",
        Icons.Default.Home
    ),
    FAVORITES(
        "Favorites",
        Icons.Default.Favorite),
    Setting(
        "Setting",
        Icons.Default.Settings),
}