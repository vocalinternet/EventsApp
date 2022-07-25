package com.eventsapp.models

import java.io.Serializable

data class EventID(
    val access_status: String? = null,
    val ad_partner_percent: Int? = null,
    val age_limit: String? = null,
    //val categories: List<Category>,
    val created_at: String? = null,
    val description_html: String? = null,
    val description_short: String? = null,
    val ends_at: String? = null,
    val id: Int? = null,
    val is_sending_free_tickets: Boolean? = null,
    val locale: String? = null,
    val location: Location? = null,
    val moderation_status: String? = null,
    val name: String? = null,
    val organization: Organization? = null,
    val properties: List<String>? = null,
    val questions: List<Question>? = null,
    val registration_data: RegistrationData? = null,
    val reservation_period: String? = null,
    val starts_at: String? = null,
    val status: String? = null,
    val ticket_types: List<TicketType>? = null,
    val tickets_limit: Int? = null,
    val url: String? = null,
    val widgets: Widgets? = null,
    val res: List<EventID>? = null,
    val address: String? = Location().address
) : Serializable

//data class Category(
//    val id: Int,
//    val name: String
//)

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
    val default_url: String,
    val uploadcare_url: String
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