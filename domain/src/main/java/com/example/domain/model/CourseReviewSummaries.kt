package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class CourseReviewSummaries(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("course-review-summaries")
    val courseReviewSummaries: List<CourseReviewSummary>
)
