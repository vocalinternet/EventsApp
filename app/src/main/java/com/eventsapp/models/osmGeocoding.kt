package com.eventsapp

class osmGeocoding : ArrayList<osmGeocodingItem>()

data class osmGeocodingItem(
    val boundingbox: List<String>,
    val `class`: String,
    val display_name: String,
    val icon: String,
    val importance: Double,
    val lat: String,
    val licence: String,
    val lon: String,
    val osm_id: Int,
    val osm_type: String,
    val place_id: Int,
    val type: String
)