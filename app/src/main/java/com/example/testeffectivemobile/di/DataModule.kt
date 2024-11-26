package com.example.testeffectivemobile.di

import com.example.common.Constant
import com.example.data.api.AuthorsAPI
import com.example.data.api.CoursesAPI
import com.example.data.repository.CoursesRepositoryImpl
import com.example.data.api.TagsAPI
import com.example.data.repository.AuthorsRepositoryImpl
import com.example.data.repository.TagsRepositoryImpl
import com.example.domain.repository.AuthorsRepository
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.TagsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCoursesApi(): CoursesAPI {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoursesAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTagsApi(): TagsAPI {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TagsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorsApi(): AuthorsAPI {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthorsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoursesRepository(api: CoursesAPI): CoursesRepository {
        return CoursesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTagsRepository(api: TagsAPI): TagsRepository {
        return TagsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthorsRepository(api: AuthorsAPI): AuthorsRepository {
        return AuthorsRepositoryImpl(api)
    }

}