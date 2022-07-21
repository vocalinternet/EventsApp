package com.eventsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var map: MapboxMap
    lateinit var permissionsManager: PermissionsManager
    lateinit var locationEngine: LocationEngine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_main)
        val mapview = findViewById<MapView>(R.id.mapview)

    }
}
