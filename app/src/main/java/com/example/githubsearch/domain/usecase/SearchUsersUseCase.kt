package com.example.githubsearch.domain.usecase

import com.example.githubsearch.domain.entity.UserEntity
import com.example.githubsearch.domain.repository.GitHubUserRepository
import jakarta.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: GitHubUserRepository
) {
    suspend operator fun invoke(query: String): List<UserEntity> {
        return repository.searchUsers(query)
    }
}