package com.example.domain.repository

import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow

interface UserFirebaseRepository {

    suspend fun signIn(email: String, password: String): Boolean

    fun signOut()

    suspend fun registration(email: String, password: String): Boolean
}