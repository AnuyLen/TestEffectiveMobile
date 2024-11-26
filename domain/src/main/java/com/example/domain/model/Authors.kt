package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Authors(
    @SerializedName("meta")
    val meta: Meta? = null,

    @SerializedName("users")
    var authors: Set<Author> = emptySet()
)