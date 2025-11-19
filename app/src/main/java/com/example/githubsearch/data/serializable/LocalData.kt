package com.example.githubsearch.data.serializable

import kotlinx.serialization.Serializable

@Serializable
data class LocalData(
    val favoriteUsers: List<User> = emptyList(),
    val isDarkTheme: Boolean = true
) {

}