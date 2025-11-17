package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.GitHubUserRepository
import com.example.githubsearch.data.serializable.User
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val repository: GitHubUserRepository
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    val favoriteUsers: StateFlow<List<User>> = repository.dataFlow
        .map { it.users }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun searchUsers(query: String) {
        viewModelScope.launch {
            runCatching {
                repository.searchUsers(query)
            }.onSuccess {
                _users.value = it
            }
        }
    }

    fun toggleFavorite(user: User) {
        viewModelScope.launch {
            repository.toggleFavorite(user)
        }
    }
}