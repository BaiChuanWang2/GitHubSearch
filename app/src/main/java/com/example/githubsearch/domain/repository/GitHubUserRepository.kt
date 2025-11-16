package com.example.githubsearch.domain.repository

import com.example.githubsearch.domain.entity.UserEntity

interface GitHubUserRepository {
    suspend fun searchUsers(query: String): List<UserEntity>
}