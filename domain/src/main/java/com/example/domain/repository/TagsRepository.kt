package com.example.domain.repository

import com.example.domain.model.Tags

interface TagsRepository {
    suspend fun getTags(page: Int, order: String?, pageSize: Int?): Tags
}