package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class CourseReviewSummary(
    @SerializedName("id")
    val id: Int,
    @SerializedName("course")
    val course: Int,
    @SerializedName("average")
    val average: Double,
    @SerializedName("count")
    val count: Int,
    @SerializedName("distribution")
    val distribution: List<Int>
)
