package com.eventsapp
/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val map = findViewById<MapView>(R.id.mapView)
        map.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }
    //fun AddtoMap()
}*/

import  android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    var opa: MutableList<Pair<Double, Double>> = mutableListOf()

    fun f (): Void? {
        var lst: List<Double> = listOf(
            55.765033332550175, 37.577827552810085,
            55.74601500011248, 37.57072196184581,
            55.747910857765596, 37.56661362594627,
            55.74217991548428, 37.55898385810676,
            55.74087815397645, 37.5353622333083,
            55.74029230318877, 37.52825974392925,
            55.74250882639969, 37.525953044030054,
            55.74450256230147, 37.53819238209041,
            55.74703286003874, 37.5386537220035,
            55.74844589049728, 37.53728043108546,
            55.75678414341971, 37.54140030370535,
            55.75691696141078, 37.530113569010716,
            55.758039859091916, 37.52895485423351,
            55.763648304358156, 37.55148182022836,
            55.767860956992955, 37.56830755094396,
            55.76704753010935, 37.572636806193,
            55.767657822970115, 37.57304647352725,
            55.76571594887846, 37.57693831328336,
            55.76525500631048, 37.57253059625243,
            55.76772038998291, 37.57938973895328,
            55.76845016707479, 37.58363055480301,
            55.769555475889135, 37.58369883269204,
            55.76950426536229, 37.58086909351409,
            55.77016572952493, 37.585428539394364,
            55.774014801344805, 37.586005108201924,
            55.773784379813996, 37.58330433833236,
            55.77710402529746, 37.59260530482605,
            55.77777816038978, 37.59172527870062,
            55.77716802596547, 37.590617659611716,
            55.779192333291654, 37.59573661803906,
            55.78099701513861, 37.59333171872376,
            55.780595981954406, 37.60164644883593,
            55.78063437892757, 37.600022952384094,
            55.78069410748297, 37.604521706739064,
            55.78081783061759, 37.60701005660245,
            55.781500433934134, 37.60585691892084,
            55.781594290964506, 37.60748800188984,
            55.7804509264003, 37.60987772823245,
            55.78017787915096, 37.609278400149535,
            55.77807101886188, 37.60657123306477,
            55.77771262206525, 37.626033792198925,
            55.778341641908625, 37.62312478417378,
            55.78027897938718, 37.632489748936834,
            55.7817955471204, 37.642391551906044,
            55.77704148444526, 37.64228329041487,
            55.77805627434606, 37.65484169096471,
            55.76918067060716, 37.65834189613881,
            55.76758707577943, 37.65713296970616,
            55.76620150883278, 37.65394826059714,
            55.76533867669927, 37.6600831112366,
            55.76430325293268, 37.664557943496064,
            55.76337439351057, 37.659938761924664,
            55.76243536013009, 37.66106649182418,
            55.762506422905254, 37.66175215159195,
            55.76147599995677, 37.65875690099964,
            55.75833380330775, 37.664242179095645,
            55.70035669868337, 37.630676428558196,
            55.70139658365333, 37.624818484357235,
            55.695967111822554, 37.625537315881736,
            55.6962996735242, 37.61685768804807,
            55.69902052671577, 37.632059,
            55.76094253385206, 37.6353098359719,
            55.76032076446457, 37.634816309520055,
            55.75962654421144, 37.63723029759975,
            55.76000260251606, 37.62997753262673,
            55.76041813079216, 37.625654547241695,
            55.75876612633412, 37.624681875530065,
            55.76003300717421, 37.621529698686814,
            55.75817827964568, 37.63984834925588,
            55.75607942893167, 37.60889736922598,
            55.7497619113646, 37.62969683556446,
            55.74425286106181, 37.63505779381427,
            55.748390436295274, 37.64504867055256,
            55.748619018821685, 37.635666993615395,
            55.744313675337864, 37.63492724105896,
            55.74515556167018, 37.62488485814754,
            55.7423161570168, 37.618928563677756,
            55.742782167957515, 37.61297259298278,
            55.742704499853865, 37.609477197053685,
            55.74077569229119, 37.60867233610143,
            55.73818647149028, 37.61817110021704,
            55.73925718874201, 37.59713814431703,
            55.741818393183436, 37.598779013217026,
            55.740747746199645, 37.599375692817034,
            55.739068240773285, 37.596168539967024,
            55.73670929901785, 37.59365954606942,
            55.734609688584264, 37.58579083384807,
            55.72984315396532, 37.59004217599807,
            55.72971715837109, 37.59000488352307,
            55.72965416042148, 37.60119262602308,
            55.72912917355479, 37.59850756782308,
            55.72446579476573, 37.61950958009575,
            55.72355988514582, 37.61848425300058,
            55.73460096493435, 37.630144490650565,
            55.72321136169728, 37.62685487053144,
            55.751065079352834, 37.59037591463896,
            55.75016688258745, 37.59307926582597,
            55.74477726764427, 37.65164101865704,
            55.74325557492942, 37.654116376370446,
            55.742082180989655, 37.64897023796626,
            55.73538949422286, 37.65027305781542,
            55.73516944119851, 37.66307326283342,
            55.73416084885799, 37.65913223286975,
            55.728035383478314, 37.62727828753185,
            55.723266391746236, 37.64020877444652,
            55.71972595595143, 37.62721314645114,
            55.71596503781098, 37.63460664908559,
            55.70917612269397, 37.621741303075126,
            55.71247898552097, 37.62040591272974,
            55.70972661119065, 37.610539532757684,
            55.705359125317024, 37.6044814204295,
            55.70029369853201, 37.60969269982614,
            55.69610020053188, 37.60008386851497,
            55.695108955633245, 37.599888445537594,
            55.69694457441166, 37.59005215547176,
            55.691804624337344, 37.58933560455472,
            55.6896199408075, 37.58454774153403,
            55.68745349552287, 37.58428717756419,
            55.691162082958165, 37.578913045686406,
            55.68996876399806, 37.57536286159744,
            55.69079491173783, 37.61096241430781,
            55.68414851723327, 37.62216666501059,
            55.67975980872609, 37.61161382426696,
            55.67353397604872, 37.614382316446424,
            55.67566446099031, 37.61047385689894,
            55.669566556819724, 37.61073442080463,
            55.66673768830744, 37.598097068267776,
            55.66918081406462, 37.59669653692993
        )

        for (i in 0..(lst.size - 1) step 2) {
            opa.add(Pair(lst[i + 1], lst[i]))
        }
        return null
    }

    private var mapView: MapView? = null
    var cnt = 0
    /*val opa:List<Pair<Double, Double>> = listOf<Pair<Double, Double>>(Pair(37.632059, 55.761347
    ), Pair(37.6353098359719,55.76094253385206 ), Pair(37.634816309520055, 55.76032076446457
    ), Pair(37.63723029759975,55.75962654421144
    ), Pair(37.62997753262673, 55.76000260251606
    ))*/
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView?.getMapboxMap()?.setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView?.getMapboxMap()?.setCamera(CameraOptions.Builder().center(it).build())
        mapView?.gestures?.focalPoint = mapView?.getMapboxMap()?.pixelForCoordinate(it)
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mapView = MapView(this)
        //setContentView(mapView)
        f()
        setContentView(R.layout.activity_main)
        mapView=findViewById(R.id.mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            val bt = findViewById<FloatingActionButton>(R.id.floating_action_button)
            onMapReady()
            bt.setOnClickListener {

                onMapReady()
            }
        }
        /*mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    for (i in opa) {
                        addAnnotationToMap(i.first, i.second)
                    }
                }
            }
        )*/
    }

    private fun onMapReady() {
        mapView?.getMapboxMap()?.setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
            for (i in opa) {
                addAnnotationToMap(i.first, i.second)
            }

        }
    }

    private fun setupGesturesListener() {
        mapView?.gestures?.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView?.location
        locationComponentPlugin?.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_puck_icon,
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
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin?.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin?.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        mapView?.location
            ?.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView?.location
            ?.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView?.gestures?.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.location
            ?.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView?.location
            ?.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView?.gestures?.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    for (i in opa) {
                        addAnnotationToMap(i.first, i.second)
                    }
                }
            }
        )
    }*/

    private fun addAnnotationToMap(x : Double, y :Double) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MainActivity,
            R.drawable.map_marker
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                .withPoint(Point.fromLngLat(x, y))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)
// Add the resulting pointAnnotation to the map.
            val pointAnnotation = pointAnnotationManager?.create(pointAnnotationOptions)
            // set click listener
            pointAnnotationManager?.addClickListener {clickedAnnotation ->
                if (pointAnnotation == clickedAnnotation) {
                    val intent = Intent(this@MainActivity, BottomFragment::class.java)
                    println("OK OK OK OK OK OK")
                    Log.d("opa", "dewd")
                    BottomFragment().show(supportFragmentManager, "tag")
                }
                true
            }
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
    }
}