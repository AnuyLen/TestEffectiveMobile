package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entity.CourseEntity
import com.example.domain.entity.CourseReviewSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courseentity WHERE isFavorite LIKE :favorite")
    fun findByFavorite(favorite: Boolean): List<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg vacancies: CourseEntity)

    @Query("SELECT * FROM coursereviewsummary WHERE id LIKE :id")
    fun findReviewById(id: Int): CourseReviewSummary

    @Query("SELECT id FROM courseentity WHERE isFavorite LIKE :favorite")
    fun findFavoritesIds(favorite: Boolean):  List<Int>
}