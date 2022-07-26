package com.eventsapp.models

import java.io.Serializable

data class AllEvents(
    val total: Int,
    val values: List<Value>
)

data class Value(
    val categories: List<Category>? = null,
    val id: Int? = null,
    val moderation_status: String? = null,
    val name: String?= null,
    val starts_at: String? = null,
    val url: String? = null
) : Serializable

data class Category(
    val id: Int,
    val name: String
)
