package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.preferences.UserPreferencesDataStore
import com.example.githubsearch.domain.entity.UserEntity
import com.example.githubsearch.domain.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users

    val favoriteUsers: StateFlow<List<UserEntity>> = userPreferencesDataStore.userListFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun searchUsers(query: String) {
        viewModelScope.launch {
            runCatching {
                searchUsersUseCase(query)
            }.onSuccess {
                _users.value = it
            }
        }
    }

    fun toggleFavorite(user: UserEntity) {
        viewModelScope.launch {
            userPreferencesDataStore.toggleFavorite(user)
        }
    }
}