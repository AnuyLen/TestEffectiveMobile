package com.example.domain.response

data class LoadingState (
    val isLoading: Boolean = false,
    val isCompleted: Boolean? = false,
    val error: String? = null
)