package com.example.domain.repository

import com.example.domain.model.CourseReviewSummaries
import com.example.domain.model.Courses
import retrofit2.http.Query

interface CoursesRepository {
    suspend fun getCourses(page: Int, language: String?, order: String?, pageSize: Int, isPopular: Boolean?): Courses

    suspend fun getCourseById(id: Int): Courses

    suspend fun getReviewCourse(course: Int): CourseReviewSummaries

    suspend fun getReviewCourses(page: Int, order: String?, pageSize: Int): CourseReviewSummaries
}