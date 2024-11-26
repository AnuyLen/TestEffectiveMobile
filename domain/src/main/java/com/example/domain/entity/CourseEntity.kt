package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Course

//import com.example.domain.model.CourseReviewSummaries

@Entity
data class CourseEntity(
    @PrimaryKey
    var id: Int,
    var title: String? = null,
    var description: String? = null,
    var cover: String? = null,

    var certificate: String? = null,
    var requirements: String? = null,
    var summary: String? = null,
    var workload: String? = null,
    var isFavorite: Boolean? = null,

    var owner: Int = 0,

    var difficulty: String? = null,

    var reviewSummary: Int = 0,

    var timeToComplete: Int? = null,

    var isPaid: Boolean = false,
    var price: String? = null,
    var displayPrice: String? = null,
    var canonicalUrl: String? = null

) {
    fun toCourse(): Course {
        return Course(
            id = id,
            title = title,
            description = description,
            cover = cover,
            certificate = certificate,
            requirements = requirements,
            summary = summary,
            workload = workload,
            isFavorite = isFavorite,
            owner = owner,
            difficulty = difficulty,
            reviewSummary = reviewSummary,
            timeToComplete = timeToComplete,
            isPaid = isPaid,
            price = price,
            displayPrice = displayPrice,
            canonicalUrl = canonicalUrl
        )
    }
}