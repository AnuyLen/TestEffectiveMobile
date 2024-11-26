package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("page")
    val page: Long? = null,

    @SerializedName("has_next")
    val hasNext: Boolean? = false,

    @SerializedName("has_previous")
    val hasPrevious: Boolean? = false
)