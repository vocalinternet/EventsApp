package com.eventsapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.common.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@RequiresApi(Build.VERSION_CODES.O)
object RetrofitClient {
    private const val TIMEPAD_URL = "https://api.timepad.ru/v1/"
    private const val MAPBOX_URL = "https://api.mapbox.com/"
    private const val OSM_URL = "https://nominatim.openstreetmap.org/"
    private const val YA_URL = "https://geocode-maps.yandex.ru/1.x/"
    private val timepadGET by lazy {
        Retrofit.Builder()
            .baseUrl(TIMEPAD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    private val mapboxGET by lazy {
        Retrofit.Builder()
            .baseUrl(MAPBOX_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val osmGET by lazy {
        Retrofit.Builder()
                .baseUrl(OSM_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    } // openstreetmap geocoder

    private val yaGET by lazy {
        Retrofit.Builder()
            .baseUrl(YA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val timepadApi: ApiService by lazy { timepadGET.create(ApiService::class.java) }
    val mapboxApi: ApiService by lazy { mapboxGET.create(ApiService::class.java) }
    val osmApi: ApiService by lazy { osmGET.create(ApiService::class.java)}
    val yaApi: ApiService by lazy { yaGET.create(ApiService::class.java)}
}

//curl -X GET "https://nominatim.openstreetmap.org/search?country=Россия&q=Петушки&limit=1&format=json"