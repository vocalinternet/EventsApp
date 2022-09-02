package com.eventsapp.Adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eventsapp.retrofit.RetrofitServices

class EventsAdapterFactory(
    private val services: RetrofitServices
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventsAdapter(services) as T
    }

}