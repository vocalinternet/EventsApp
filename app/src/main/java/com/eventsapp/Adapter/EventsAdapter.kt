package com.eventsapp.Adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventsapp.MainActivity
import com.eventsapp.models.*
import com.eventsapp.retrofit.RetrofitServices
import kotlinx.coroutines.launch
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class EventsAdapter(private val services: RetrofitServices ) : ViewModel() {

    var eventsResponse: MutableLiveData<Response<AllEvents>> = MutableLiveData()

    fun getEvents() {
        viewModelScope.launch {
            var timepadEventsResponse = services.getEvents()
            eventsResponse.postValue(timepadEventsResponse)
            //eventsResponse.value = timepadEventsResponse
        }
    }

//    var currentEventResponse: MutableLiveData<Response<EventID>> = MutableLiveData()
//    fun getEventID() {
//        viewModelScope.launch {
//       // var timepadEventIDResponse = services.getEventID()
//            currentEventResponse.postValue(timepadEventIDResponse)
//            //currentEventResponse.value = timepadEventIDResponse
//    }
//    }

//    var LatLonResponse: MutableLiveData<Response<GeocodingLocation>> = MutableLiveData()
//    fun getCoordsLocation() {
//        viewModelScope.launch {
//            var LatLonEventResponse = services.getCoordsLocation()
//            LatLonResponse.postValue(LatLonEventResponse)
//            //LatLonResponse.value = LatLonEventResponse
//        }

    }


    //var listEvents: List<Value>? = AllEvents().values
    //var currentEvent: List<EventID>? = EventID().res
   // var LatLonEvent: List<Double>? = Feature().center








