package com.eventsapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@RequiresApi(Build.VERSION_CODES.O)
object RetrofitClient {
    private const val TIMEPAD_URL = "https://api.timepad.ru/v1/"
    private const val MAPBOX_URL = "https://api.mapbox.com/"
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

    val timepadApi: ApiService by lazy { timepadGET.create(ApiService::class.java) }
    val mapboxApi: ApiService by lazy { mapboxGET.create(ApiService::class.java) }
}