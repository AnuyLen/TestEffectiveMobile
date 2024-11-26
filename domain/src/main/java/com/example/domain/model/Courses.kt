package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Courses(
    @SerializedName("meta")
    var meta: Meta? = null,

    @SerializedName("courses")
    var courses: Set<Course> = emptySet(),

    @SerializedName("enrollments")
    val enrollments: List<String> = emptyList()

)
