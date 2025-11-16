package com.example.githubsearch.di

import com.example.githubsearch.domain.model.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchUsersResponse

}