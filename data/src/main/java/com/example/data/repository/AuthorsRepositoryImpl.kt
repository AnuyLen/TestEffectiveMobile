package com.example.data.repository

import com.example.data.api.AuthorsAPI
import com.example.domain.model.Authors
import com.example.domain.repository.AuthorsRepository
import javax.inject.Inject

class AuthorsRepositoryImpl @Inject constructor(
    private val api: AuthorsAPI
): AuthorsRepository {
    override suspend fun getAuthorById(id: Int): Authors {
        return api.getAuthorById(id)
    }
}