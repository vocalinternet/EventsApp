package com.eventsapp.models

import java.io.Serializable

data class EventID(
    val access_status: String,
    val ad_partner_percent: Int,
    val age_limit: String,
    //val categories: List<Category>,
    val created_at: String,
    val description_html: String,
    val description_short: String,
    val ends_at: String,
    val id: Int,
    val is_sending_free_tickets: Boolean,
    val locale: String,
    val location: Location,
    val moderation_status: String,
    val name: String,
    val organization: Organization,
    val properties: List<String>,
    val questions: List<Question>,
    val registration_data: RegistrationData,
    val reservation_period: String,
    val starts_at: String,
    val status: String,
    val ticket_types: List<TicketType>,
    val tickets_limit: Int,
    val url: String,
    val widgets: Widgets,
    val poster_image: PosterImage? = null
) : Serializable

//data class Category(
//    val id: Int,
//    val name: String
//)

data class PosterImage(
    val default_url: String? = null,
    val uploadcare_url: String? = null
)

data class Location(
    val address: String? = null,
    val city: String? = null,
    val country: String? = null
) : Serializable

data class Organization(
    val description_html: String,
    val id: Int,
    val logo_image: LogoImage,
    val name: String,
    val subdomain: String,
    val url: String
)

data class Question(
    val field_id: String,
    val is_for_every_visitor: Boolean,
    val is_mandatory: Boolean,
    val meta: Meta,
    val name: String,
    val type: String
)

data class RegistrationData(
    val is_registration_open: Boolean,
    val price_max: Int,
    val price_min: Int,
    val sale_ends_at: String,
    val tickets_total: Int
)

data class TicketType(
    val ad_partner_profit: Int,
    val buy_amount_max: Int,
    val buy_amount_min: Int,
    val description: String,
    val id: Int,
    val is_active: Boolean,
    val is_promocode_locked: Boolean,
    val name: String,
    val price: Int,
    val remaining: Int,
    val sale_ends_at: String,
    val send_personal_links: Boolean,
    val status: String
)

data class Widgets(
    val button: Button,
    val custom: Custom
)

data class LogoImage(
    val default_url: String? = null,
    val uploadcare_url: String? = null
)

data class Meta(
    val block: Boolean
)

data class Button(
    val code_html: String
)

data class Custom(
    val code_html: String
)