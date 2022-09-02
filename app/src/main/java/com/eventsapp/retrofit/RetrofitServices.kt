package com.eventsapp.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.eventsapp.MainActivity
import com.eventsapp.models.*

import retrofit2.Response
import java.net.URLEncoder
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.util.concurrent.TimeUnit
@RequiresApi(Build.VERSION_CODES.O)

class RetrofitServices{

    private val timeStamp = LocalDate.parse("2022-07-25",DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString()
    //val timeStamp = ISO_LOCAL_DATE.withZone(ZoneId.from(ZoneOffset.UTC)).format(Instant.now().plus).
    //val timeStamp: String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(
     //   Instant.now().plusSeconds(
     //       TimeUnit.DAYS.toSeconds(1)))
    var skipInt: Int? = 0


    suspend fun getEvents(): Response<AllEvents> {
        return RetrofitClient.timepadApi.getEvents(timeStamp,skipInt)
    }

   // suspend fun getEventID(): Response<EventID> {
   //     return RetrofitClient.timepadApi.getEventID(MainActivity().resId)
   // }

//    suspend fun getCoordsLocation(): Response<GeocodingLocation> {
//        return RetrofitClient.mapboxApi.getCoordsLocation(MainActivity().urlencode)
//    }
}