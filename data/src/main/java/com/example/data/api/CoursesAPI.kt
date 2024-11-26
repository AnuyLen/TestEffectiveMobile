package com.example.data.api

import com.example.domain.model.CourseReviewSummaries
import com.example.domain.model.Courses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoursesAPI {
    @GET("courses?with_certificate=true")
    suspend fun getCoursesSortedByDate(
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("order") order: String?,
        @Query("page_size") pageSize: Int,
        @Query("is_popular") isPopular: Boolean?
    ): Courses

    @GET("courses/{id}")
    suspend fun getCourseById(
        @Path("id") id: Int,
    ): Courses

    @GET("course-review-summaries")
    suspend fun getReviewCourse(
        @Query("course") course: Int
    ): CourseReviewSummaries

    @GET("course-review-summaries")
    suspend fun getReviewCourses(
        @Query("page") page: Int,
        @Query("order") order: String?,
        @Query("page_size") pageSize: Int
    ): CourseReviewSummaries
}