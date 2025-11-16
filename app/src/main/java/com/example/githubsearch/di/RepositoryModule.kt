package com.example.githubsearch.di

import com.example.githubsearch.data.repository.GitHubUserRepositoryImpl
import com.example.githubsearch.domain.repository.GitHubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGitHubUserRepository(api: GitHubApi): GitHubUserRepository =
        GitHubUserRepositoryImpl(api)
}