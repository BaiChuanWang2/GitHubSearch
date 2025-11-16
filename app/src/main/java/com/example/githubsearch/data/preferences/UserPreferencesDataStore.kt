package com.example.githubsearch.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubsearch.domain.entity.UserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "github_search_data")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val userListFlow: Flow<List<UserEntity>> = context.dataStore.data
        .map { prefs ->
            prefs[UserPreferencesKeys.USER_LIST]?.let {
                Json.decodeFromString<List<UserEntity>>(it)
            } ?: emptyList()
        }

    suspend fun toggleFavorite(
        user: UserEntity
    ) {
        context.dataStore.edit { prefs ->
            val currentListJson = prefs[UserPreferencesKeys.USER_LIST]
            val currentList: MutableList<UserEntity> = if (currentListJson != null) {
                Json.decodeFromString(currentListJson)
            } else mutableListOf()

            if (currentList.any { it.id == user.id }) {
                currentList.removeAll { it.id == user.id }
            } else {
                currentList.add(user)
            }

            prefs[UserPreferencesKeys.USER_LIST] = Json.encodeToString(currentList)
        }
    }
}