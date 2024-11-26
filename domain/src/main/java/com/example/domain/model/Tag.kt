package com.example.domain.model

data class Tag (
    val id: Long,
    val title: String,
    val translations: Translations,
    val parent: Long? = null,
    val parents: List<Long>,
    val children: List<Long>
)