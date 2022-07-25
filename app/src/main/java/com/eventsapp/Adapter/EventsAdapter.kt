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


class EventsAdapter(private val services: RetrofitServices ) : ViewModel() {

    var eventsResponse: MutableLiveData<Response<AllEvents>> = MutableLiveData()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getEvents() {
        viewModelScope.launch {
            var timepadEventsResponse = services.getEvents()
            eventsResponse.value = timepadEventsResponse
        }
    }

    var currentEventResponse: MutableLiveData<Response<EventID>> = MutableLiveData()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventID() {
        viewModelScope.launch {
        var timepadEventIDResponse = services.getEventID()
        currentEventResponse.value = timepadEventIDResponse
    }
    }

    var LatLonResponse: MutableLiveData<Response<Feature>> = MutableLiveData()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCoordsLocation() {
        viewModelScope.launch {
            var LatLonEventResponse = services.getCoordsLocation()
            LatLonResponse.value = LatLonEventResponse
        }

    }

    //var listEvents: List<Value>? = AllEvents().values
    //var currentEvent: List<EventID>? = EventID().res
   // var LatLonEvent: List<Double>? = Feature().center








}