package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CourseReviewSummary(
    @PrimaryKey
    val id: Long,
    val course: Long,
    val average: Double,
    val count: Long
)