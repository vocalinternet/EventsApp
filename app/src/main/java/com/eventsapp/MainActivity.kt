package com.eventsapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.eventsapp.Adapter.EventsAdapter
import com.eventsapp.databinding.ActivityMainBinding
import com.eventsapp.retrofit.RetrofitClient
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.net.URLEncoder
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates


private lateinit var adapterService: EventsAdapter


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var locationPermissionHelper: LocationPermissionHelper


    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    fun Any?.toEncode(): String = URLEncoder.encode(String.toString(), "utf-8")
    lateinit var urlencode: String
    var resId by Delegates.notNull<String>()
    var skipInt: Int = 0

    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = MapView(this)
        //setContentView(mapView)
        mapboxMap = binding.mapView.getMapboxMap()
        mapboxMap.getStyle()?.localizeLabels(Locale("ru", "RU"))
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }}
        private fun onMapReady() {
            mapView.getMapboxMap().setCamera(
                CameraOptions.Builder()
                    .zoom(13.0)
                    .build()
            )//"mapbox://styles/vocalinternet/cl5s5ifkr000914sesoqorttn"
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, object : Style.OnStyleLoaded{

                override fun onStyleLoaded(style: Style) {

                    mapView.location.updateSettings {
                        enabled = true
                        pulsingEnabled = true
                    }
                    initLocationComponent()
                    setupGesturesListener()
                    val opa: MutableList<Pair<Double, Double>> = mutableListOf()


                    val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")

                    val today = Date()

                    val todayWithZeroTime: Date = formatter.parse(formatter.format(today))
                    val timeStamp = LocalDate.parse(formatter.format(today), DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString()
                    var total = 1000
                    var cur = 0
                    CoroutineScope(Dispatchers.IO).launch {
                        while (skipInt < total){
                        var limit = 100
                        var getevents =  RetrofitClient.timepadApi.getEvents(timeStamp,skipInt)
                        total = getevents.body()!!.total!!
                            println("total ${total}")
                        println("mess ${getevents.message()}")
                        if (getevents.isSuccessful){

                        while (cur < total) {
                            cur++
                            println("total ${total}")
                            println("currr $cur")
                            var geteventid = RetrofitClient.timepadApi.getEventID(getevents.body()!!.values!![cur % 100].id)
                            print("id $geteventid")
                            println("body ${geteventid.body()}")
                            println("values ${getevents.body()!!.values}")
                            print("get ${getevents.body()!!.values?.lastIndex}")

                            //if(geteventid.isSuccessful ) {
//                                var odds = geteventid.body()?.location?.address!!.split(" ").take(20).joinToString(separator = " ")
//                                println(odds)

                            var getcoordsbyid =
                                RetrofitClient.yaApi.getLL("${geteventid.body()?.location?.country!!} ${geteventid.body()?.location?.city!!} ${geteventid.body()?.location?.address!!}")
                            println("hbujbby $getcoordsbyid ")
                            var pairLL =
                                getcoordsbyid.body()?.response!!.GeoObjectCollection.featureMember.get(
                                    0
                                ).GeoObject.Point.pos.split(" ")
//                            when (i) {
//                                99 -> {
//                                    skipInt += 100
//                                }
//                            }
                            println( "pair $pairLL ")
                            opa += Pair(pairLL[0].toDouble(), pairLL[1].toDouble())
                            println("opa $opa")
                            //resId = geteventid.body()?.poster_image!!.default_url.toString()
                            skipInt += 100
                        }
                        }}};
                    for (j in opa) addAnnotationToMap(j.first, j.second)
                }
        })
        }








        private fun addAnnotationToMap(x : Double, y :Double) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
            bitmapFromDrawableRes(
                this@MainActivity,
                R.drawable.red_marker
            )?.let {
                val annotationApi = mapView.annotations
                val pointAnnotationManager = annotationApi.createPointAnnotationManager()
// Set options for the resulting symbol layer.
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                    .withPoint(Point.fromLngLat(x, y))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                    .withIconImage(it)
// Add the resulting pointAnnotation to the map.
                val pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)
            pointAnnotationManager.addClickListener {clickedAnnotation ->
                if (pointAnnotation == clickedAnnotation) {
                    val intent = Intent(this@MainActivity, BottomFragment::class.java)
                    println("OK OK OK OK OK OK")
                    Log.d("opa", "dewd")
                    BottomFragment().show(supportFragmentManager, "tag")
                }
                true
            }
        }}
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
        }
//    private fun onMapReady() {
//        mapView.getMapboxMap().setCamera(
//            CameraOptions.Builder()
//                .zoom(3.0)
//                .build()
//        )
//        mapView.getMapboxMap().loadStyleUri(
//            Style.MAPBOX_STREETS
//        ) {
//            initLocationComponent()
//            setupGesturesListener()
//        }
//    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MainActivity, //@LocationTrackingActivity
                    com.mapbox.maps.R.drawable.mapbox_user_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(3.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        //Toast.makeText(this, "аа3ааХЪ", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
