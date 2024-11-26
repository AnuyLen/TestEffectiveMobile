package com.example.data.api

import com.example.domain.model.Tags
import retrofit2.http.GET
import retrofit2.http.Query

interface TagsAPI {
    @GET("tags")
    suspend fun getCourses(
        @Query("page") page: Int,
        @Query("order") order: String?,
        @Query("page_size") pageSize: Int?
    ): Tags
}