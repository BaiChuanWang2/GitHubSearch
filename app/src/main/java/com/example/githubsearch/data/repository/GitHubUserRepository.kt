package com.example.githubsearch.data.repository

import androidx.datastore.core.DataStore
import com.example.githubsearch.data.api.GitHubApi
import com.example.githubsearch.data.serializable.Favorites
import com.example.githubsearch.data.serializable.User
import jakarta.inject.Inject

class GitHubUserRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val dataStore: DataStore<Favorites>
) {
    val dataFlow = dataStore.data
    suspend fun searchUsers(query: String): List<User> =
        gitHubApi.searchUsers(query).items

    suspend fun toggleFavorite(user: User) {
        dataStore.updateData { current ->
            val exists = current.users.any { it.id == user.id }

            if (exists) {
                current.copy(
                    users = current.users.filter { it.id != user.id }
                )
            } else {
                current.copy(
                    users = current.users + user
                )
            }
        }
    }
}