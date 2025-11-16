package com.example.githubsearch.data.repository

import com.example.githubsearch.di.GitHubApi
import com.example.githubsearch.domain.entity.UserEntity
import com.example.githubsearch.domain.repository.GitHubUserRepository
import jakarta.inject.Inject

class GitHubUserRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : GitHubUserRepository {

    override suspend fun searchUsers(query: String): List<UserEntity> =
        gitHubApi.searchUsers(query).items
}