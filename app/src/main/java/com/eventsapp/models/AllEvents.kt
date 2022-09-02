package com.eventsapp.models

import java.io.Serializable

data class AllEvents(
    val total: Int,
    val values: List<Value>,

)

data class Value(
    val categories: List<Category>,
    val id: Int,
    val moderation_status: String,
    val name: String,
    val poster_image: PosterImage,
    val starts_at: String,
    val url: String
) : Serializable

data class Category(
    val id: Int,
    val name: String
)

//data class PosterImage(
//    val default_url: String,
//    val uploadcare_url: String
//)