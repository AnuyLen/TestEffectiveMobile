package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.CourseDao
import com.example.domain.entity.CourseEntity
import com.example.domain.entity.CourseReviewSummary

@Database(
    entities = [CourseEntity::class, CourseReviewSummary::class],
    version = 2
)
abstract class Database : RoomDatabase() {

    abstract fun coursesDao(): CourseDao

    companion object {
        const val DATABASE_NAME = "test_db"
    }
}
