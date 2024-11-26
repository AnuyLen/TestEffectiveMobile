package com.example.testeffectivemobile.di

import com.example.data.repository.UserFirebaseRepositoryImpl
import com.example.domain.repository.UserFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideUserRepository(auth: FirebaseAuth): UserFirebaseRepository {
        return UserFirebaseRepositoryImpl(auth)
    }

}