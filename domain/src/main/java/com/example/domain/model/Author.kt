package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Author (
    @SerializedName("id")
    val id: Long? = null,
    
//    @SerializedName("profile")
//    val profile: Long? = null,

//    @SerializedName("is_private")
//    val isPrivate: Boolean? = null,

//    @SerializedName("is_active")
//    val isActive: Boolean? = null,

//    @SerializedName("is_guest")
//    val isGuest: Boolean? = null,

//    @SerializedName("is_organization")
//    val isOrganization: Boolean? = null,

//    @SerializedName("is_author")
//    val isAuthor: Boolean? = null,

    @SerializedName("short_bio")
    val shortBio: String? = null,

    @SerializedName("details")
    val details: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("full_name")
    val fullName: String? = null,

    @SerializedName("alias")
    val alias: String? = null,

    @SerializedName("avatar")
    val avatar: String? = null,

//    @SerializedName("city")
//    val city: Int? = null,
//
//    @SerializedName("knowledge")
//    val knowledge: Long? = null,
//
//    @SerializedName("knowledge_rank")
//    val knowledgeRank: Long? = null,
//
//    @SerializedName("reputation")
//    val reputation: Long? = null,
//
//    @SerializedName("reputation_rank")
//    val reputationRank: Long? = null,
//
//    @SerializedName("join_date")
//    val joinDate: String? = null,
//
//    @SerializedName("social_profiles")
//    val socialProfiles: List<Long>? = null,
//
//    @SerializedName("solved_steps_count")
//    val solvedStepsCount: Long? = null,
//
//    @SerializedName("created_courses_count")
//    val createdCoursesCount: Long? = null,
//
//    @SerializedName("created_lessons_count")
//    val createdLessonsCount: Long? = null,
//
//    @SerializedName("issued_certificates_count")
//    val issuedCertificatesCount: Long? = null,
//
//    @SerializedName("followers_count")
//    val followersCount: Long? = null
)