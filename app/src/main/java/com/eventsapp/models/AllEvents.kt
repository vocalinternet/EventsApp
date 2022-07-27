package com.eventsapp.models

import java.io.Serializable

data class AllEvents(
    val total: Int? = null,
    val values: List<Value>? = null,
    val id: Int? = Value().id
)

data class Value(
    val categories: List<Category>? = null,
    val id: Int? = null,
    val moderation_status: String? = null,
    val name: String?= null,
    val poster_image: PosterImage? = null,
    val starts_at: String? = null,
    val url: String? = null
) : Serializable

data class Category(
    val id: Int,
    val name: String
)

//data class PosterImage(
//    val default_url: String,
//    val uploadcare_url: String
//)