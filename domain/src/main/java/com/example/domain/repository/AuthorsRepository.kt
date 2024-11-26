package com.example.domain.repository

import com.example.domain.model.Authors

interface AuthorsRepository {
    suspend fun getAuthorById(id: Int): Authors
}