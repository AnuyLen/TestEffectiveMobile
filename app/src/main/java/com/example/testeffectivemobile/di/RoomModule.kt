package com.example.testeffectivemobile.di

import android.app.Application
import androidx.room.Room
import com.example.data.db.Database
import com.example.data.repository.CourseRoomRepositoryImpl
import com.example.domain.repository.CourseRoomRepository
import com.example.domain.repository.CoursesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            Database::class.java,
            Database.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideCourseRepository(database: Database): CourseRoomRepository =
        CourseRoomRepositoryImpl(database.coursesDao())
}