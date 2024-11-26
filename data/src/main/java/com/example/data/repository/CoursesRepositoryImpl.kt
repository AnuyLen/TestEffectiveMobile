package com.example.data.repository

import com.example.data.api.CoursesAPI
import com.example.domain.model.Course
import com.example.domain.model.CourseReviewSummaries
import com.example.domain.model.Courses
import com.example.domain.repository.CoursesRepository
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesAPI
) : CoursesRepository {

    override suspend fun getCourses(
        page: Int,
        language: String?,
        order: String?,
        pageSize: Int,
        isPopular: Boolean?
    ): Courses {
        return api.getCoursesSortedByDate(page, language, order, pageSize, isPopular)
    }

    override suspend fun getCourseById(id: Int): Courses {
        return api.getCourseById(id)
    }


    override suspend fun getReviewCourse(course: Int): CourseReviewSummaries {
        return api.getReviewCourse(course)
    }

    override suspend fun getReviewCourses(
        page: Int,
        order: String?,
        pageSize: Int
    ): CourseReviewSummaries {
        return api.getReviewCourses(page, order, pageSize)
    }

}