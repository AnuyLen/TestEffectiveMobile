package com.example.data.repository

import com.example.domain.repository.UserFirebaseRepository
import com.example.domain.response.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserFirebaseRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user != null
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun registration(email: String, password: String): Boolean {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user != null
    }

}