package com.example.domain.repository

import com.example.domain.entity.CourseEntity
import com.example.domain.entity.CourseReviewSummary
import kotlinx.coroutines.flow.Flow

interface CourseRoomRepository {

    fun findByFavorite(favorite: Boolean): List<CourseEntity>

    fun insertAll(vararg vacancies: CourseEntity)

    fun findReviewById(id: Int): CourseReviewSummary

    fun findFavoritesIds(favorite: Boolean):  List<Int>
}