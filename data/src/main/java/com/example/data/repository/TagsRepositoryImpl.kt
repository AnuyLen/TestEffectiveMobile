package com.example.data.repository

import com.example.data.api.TagsAPI
import com.example.domain.model.Tags
import com.example.domain.repository.TagsRepository
import javax.inject.Inject

class TagsRepositoryImpl @Inject constructor(
    private val api: TagsAPI
): TagsRepository {
    override suspend fun getTags(
        page: Int,
        order: String?,
        pageSize: Int?
    ): Tags {
        return api.getCourses(page, order, pageSize)
    }

}