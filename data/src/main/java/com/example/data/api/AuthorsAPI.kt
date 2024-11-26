package com.example.data.api

import com.example.domain.model.Authors
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthorsAPI {
    @GET("users/{id}")
    suspend fun getAuthorById(
        @Path("id") id: Int
    ): Authors
}