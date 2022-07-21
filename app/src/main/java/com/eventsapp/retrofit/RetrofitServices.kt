package com.eventsapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.eventsapp.models.*
import retrofit2.Response
import java.net.URLEncoder
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class RetrofitServices{
    @RequiresApi(Build.VERSION_CODES.O)
    val timeStamp: String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(
        Instant.now().plusSeconds(
            TimeUnit.DAYS.toSeconds(1)))
    var skipInt: Int? = 0

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEvents(): Response<AllEvents> {
        return RetrofitClient.timepadApi.getEvents(timeStamp,skipInt)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEventID(): Response<EventID> {
        return RetrofitClient.timepadApi.getEventID(Value().id)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCoordsLocation(): Response<GeocodingLocation> {
        return RetrofitClient.mapboxApi.getCoordsLocation(URLEncoder.encode(Location().address, "utf-8").toString()
        )
    }
}