package com.example.githubsearch.data.api

import com.example.githubsearch.data.api.response.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchUsersResponse
}