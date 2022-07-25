package com.eventsapp.retrofit

import android.os.Build
import android.util.TimeUtils
import androidx.annotation.RequiresApi
import com.eventsapp.models.AllEvents
import com.eventsapp.models.EventID
import com.eventsapp.models.GeocodingLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path



@RequiresApi(Build.VERSION_CODES.O)
interface ApiService {

    @Headers("Accept: application/json",
        "Authorization: Bearer 9c656d513c21316fdc77e5ff886cfc0f0e6f1f03")
    @GET("events?starts_at_max={timestamp}&cities=Москва%limit=100&skip={skipInt}")
    suspend fun getEvents(@Path("timestamp") timeStamp: String,@Path("skipInt") skipInt: Int?): Response<AllEvents>

    @Headers("Accept: application/json",
        "Authorization: Bearer 9c656d513c21316fdc77e5ff886cfc0f0e6f1f03")
    @GET("events/{event_id}")
    suspend fun getEventID(@Path("event_id") id: Int?): Response<EventID>

    @GET("geocoding/v5/mapbox.places/{urlencode}.json?country=ru&limit=1&proximity=ip&types=place%2Cpostcode%2Caddress&access_token=pk.eyJ1Ijoidm9jYWxpbnRlcm5ldCIsImEiOiJjbDVyeXE4NGQwaTZoM2puMGY5am1xbnRoIn0.PV25RxZ_UUxxZx7Y5fbkoA")
    suspend fun getCoordsLocation(@Path("urlencode") address: String?): Response<GeocodingLocation>


    companion object {
        lateinit var api: ApiService
    }

}
//curl "https://api.mapbox.com/geocoding/v5/mapbox.places/%D0%BF%D0%B5%D1%82%D1%83%D1%88%D0%BA%D0%B8.json?country=ru&limit=1&proximity=ip&types=place%2Cpostcode%2Caddress&access_token=pk.eyJ1Ijoidm9jYWxpbnRlcm5ldCIsImEiOiJjbDVyeXE4NGQwaTZoM2puMGY5am1xbnRoIn0.PV25RxZ_UUxxZx7Y5fbkoA"
//{"type":"FeatureCollection","query":["петушки"],"features":[{"id":"place.17322736563947470","type":"Feature","place_type":["place"],"relevance":1,"properties":{"wikidata":"Q179372"},"text":"Петушки","place_name":"Петушки, Петушинский район, Vladimir, Russia","bbox":[39.409553978,55.909932119,39.50563287,55.949836025],"center":[39.46667,55.93333],"geometry":{"type":"Point","coordinates":[39.46667,55.93333]},
// "context":[{"id":"district.12594763086198480","wikidata":"Q1654411","text":"Петушинский район"},
// {"id":"region.12166129145721350","short_code":"RU-VLA","wikidata":"Q2702","text":"Vladimir"},{"id":"country.36095529422520","wikidata":"Q159","short_code":"ru","text":"Russia"}]}],"attribution":"NOTICE: © 2022 Mapbox and its suppliers. All rights reserved. Use of this data is subject to the Mapbox Terms of Service (https://www.mapbox.com/about/maps/). This response and the information it contains may not be retained. POI(s) provided by Foursquare."}


//curl -X GET "https://api.timepad.ru/v1/events?starts_at_max=2022-07-21&cities=Москва"
// -H  "accept: application/json" -H "Authorization: Bearer 9c656d513c21316fdc77e5ff886cfc0f0e6f1f03"