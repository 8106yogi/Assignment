package com.example.github.repositories.network

import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.model.Response
import com.example.github.repositories.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubEndpoints {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): retrofit2.Response<Response>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): retrofit2.Response<UserDTO>

    @GET
    suspend fun getUserRepositories(
        @Url userRepo: String
    ): retrofit2.Response<List<RepositoryDTO>>
}