package com.eventsapp.models

data class GeocodingLocation(
    val attribution: String,
    val features: List<Feature>,
    val query: List<String>,
    val type: String
)

data class Feature(
    val bbox: List<Double>? = null,
    val center: List<Double>? = null,
    val context: List<Context>? = null,
    val geometry: Geometry? = null,
    val id: String? = null,
    val place_name: String? = null,
    val place_type: List<String>? = null,
    val properties: Properties? = null,
    val relevance: Int? = null,
    val text: String? = null,
    val type: String? = null
)

data class Context(
    val id: String,
    val short_code: String,
    val text: String,
    val wikidata: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val wikidata: String
)