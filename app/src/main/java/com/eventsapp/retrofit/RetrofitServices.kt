package com.eventsapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.eventsapp.models.EventID
import com.eventsapp.models.AllEvents
import com.eventsapp.models.Location
import com.eventsapp.models.Value
import retrofit2.Response
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class RetrofitServices{
    @RequiresApi(Build.VERSION_CODES.O)
    val timeStamp: String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(
        Instant.now().plusSeconds(
            TimeUnit.DAYS.toSeconds(1)))

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEvents(): Response<AllEvents> {
        return RetrofitClient.timepadApi.getEvents(timeStamp)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEventID(): Response<EventID> {
        return RetrofitClient.timepadApi.getEventID(Value().id)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCoordsLocation(): Response<> {
        return RetrofitClient.mapboxApi.getCoordsLocation()
}