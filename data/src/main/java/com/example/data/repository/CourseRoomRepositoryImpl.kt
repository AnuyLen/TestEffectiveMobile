package com.example.data.repository

import com.example.data.dao.CourseDao
import com.example.domain.entity.CourseEntity
import com.example.domain.entity.CourseReviewSummary
import com.example.domain.repository.CourseRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRoomRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao
): CourseRoomRepository {
    override fun findByFavorite(favorite: Boolean): List<CourseEntity> {
        return courseDao.findByFavorite(favorite)
    }

    override fun insertAll(vararg vacancies: CourseEntity) {
        return courseDao.insertAll(*vacancies)
    }

    override fun findReviewById(id: Int): CourseReviewSummary {
        return courseDao.findReviewById(id)
    }

    override fun findFavoritesIds(favorite: Boolean): List<Int> {
        return courseDao.findFavoritesIds(favorite)
    }

}