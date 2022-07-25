package com.eventsapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.eventsapp.Adapter.EventsAdapter
import com.eventsapp.Adapter.EventsAdapterFactory
import com.eventsapp.models.Location
import com.eventsapp.retrofit.RetrofitServices
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.geojson.Point.fromLngLat
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.launch
import java.lang.reflect.Executable
import java.net.URLEncoder
import kotlin.properties.Delegates


var mapView: MapView? = null
private lateinit var adapterService: EventsAdapter


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    lateinit var urlencode: String
    var resId by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val service = RetrofitServices()
        val eventsAdapterFactory = EventsAdapterFactory(service)

        adapterService = ViewModelProvider(this, eventsAdapterFactory)[EventsAdapter::class.java]
        //adapterService.getEvents()
       // adapterService.eventsResponse.observe(this, Observer { response -> Log.d("AAA", listOf(response.body()!!.values).toString() )})
            //for(i in 0..response.body()!!.total!!) { adapterService.getEventID()
        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    var opa: List<Double>? = null
                    adapterService.getEvents()

                    adapterService.viewModelScope.launch {
                        adapterService.getEvents()
                    }

                    adapterService.eventsResponse.observe(this@MainActivity, Observer { response ->
                        Log.d("TAG", response.body()!!.total!!.toString())
                        Log.d("TAG", response.body()?.id!!.toString())
                        for (i in 0..response.body()!!.total!!) {

                            resId = response.body()?.id!!
                            adapterService.viewModelScope.launch {
                                adapterService.getEventID()
                            }
                            adapterService.currentEventResponse.observe(this@MainActivity) { responseID ->
                                Log.d("TAG", responseID.body()?.address.toString())
                                urlencode = URLEncoder.encode(responseID.body()?.address.toString(), "utf-8")


                            }
                            //URLEncoder.encode(Location().address,"utf-8"))
                        }
                    })
                    adapterService.viewModelScope.launch {
                        adapterService.getCoordsLocation()
                    }

                    adapterService.LatLonResponse.observe(this@MainActivity, Observer { response ->
                        opa = response.body()?.center
                        print(response.body()?.center)
                    })
                    if(opa != null){
                    for (i in 0..(opa!!.size)) {
                        addAnnotationToMap(opa!![0], opa!![1])
                    }
                    }else {Log.d("TAG", opa.toString())
                    Log.d("TAG",urlencode.toString())}
                }
            })
            }




        private fun addAnnotationToMap(x : Double, y :Double) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
            bitmapFromDrawableRes(
                this@MainActivity,
                R.drawable.red_marker
            )?.let {
                val annotationApi = mapView?.annotations
                val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
// Set options for the resulting symbol layer.
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                    .withPoint(Point.fromLngLat(x, y))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                    .withIconImage(it)
// Add the resulting pointAnnotation to the map.
                pointAnnotationManager?.create(pointAnnotationOptions)
            }
        }
        private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
            convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

        private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
            if (sourceDrawable == null) {
                return null
            }
            return if (sourceDrawable is BitmapDrawable) {
                sourceDrawable.bitmap
            } else {
// copying drawable object to not manipulate on the same reference
                val constantState = sourceDrawable.constantState ?: return null
                val drawable = constantState.newDrawable().mutate()
                val bitmap: Bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }
        }}