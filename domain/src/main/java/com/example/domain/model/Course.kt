package com.example.domain.model

import com.example.domain.entity.CourseEntity
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Course(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("cover")
    val cover: String? = null,

    @SerializedName("acquired_skills")
    val acquiredSkills: List<String>? = null,
    @SerializedName("certificate")
    val certificate: String? = null,
    @SerializedName("requirements")
    val requirements: String? = null,
    @SerializedName("summary")
    val summary: String? = null,
    @SerializedName("workload")
    val workload: String? = null,
    @SerializedName("isFavorite")
    var isFavorite: Boolean? = null,

    @SerializedName("authors")
    val authors: List<Int>? = null,
    @SerializedName("instructors")
    val instructors: List<Int>? = null,
    @SerializedName("sections")
    val sections: List<Int>? = null,

    @SerializedName("owner")
    val owner: Int = 0,

    @SerializedName("difficulty")
    val difficulty: String? = null,

    @SerializedName("review_summary")
    val reviewSummary: Int = 0,

    @SerializedName("time_to_complete")
    val timeToComplete: Int? = null,

    @SerializedName("is_paid")
    val isPaid: Boolean = false,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("display_price")
    val displayPrice: String? = null,
    @SerializedName("tags")
    val tags: List<Int>? = null,
    @SerializedName("canonical_url")
    val canonicalUrl: String? = null,
    @SerializedName("create_date")
    val createDate: Date? = null,

    var reviewSummaries: CourseReviewSummaries? = null
) {
    fun toCourseEntity(): CourseEntity {
        return CourseEntity(
            id,
            title,
            description,
            cover,
            certificate,
            requirements,
            summary,
            workload,
            isFavorite,
            owner,
            difficulty,
            reviewSummary,
            timeToComplete,
            isPaid,
            price,
            displayPrice,
            canonicalUrl
        )
    }
}