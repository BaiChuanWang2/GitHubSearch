package com.example.githubsearch.data.serializable

import kotlinx.serialization.Serializable

@Serializable
data class Favorites(
    val users: List<User> = emptyList()
) {

}