package com.pjatk.pawelkuklinski.miniprojekt1

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.pjatk.pawelkuklinski.miniprojekt1.R
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityMapsBinding
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: PlaceViewModel
    private lateinit var userUid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        userUid = intent.getStringExtra("userUid")!!
        val geoCoder = Geocoder(this)



        val geoClient = LocationServices.getGeofencingClient(this)
        var id = 0
        btFavorites.setOnClickListener{
            val placesIntent = Intent(this, PlacesActivity::class.java)
            placesIntent.putExtra("userUid", userUid)
            startActivity(placesIntent)
        }
        binding.btAdd.setOnClickListener{
            LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    var addressList: List<Address>? = null
                    val location = binding.etPlace.text.toString()
                    try {
                        addressList = geoCoder.getFromLocationName(location, 1)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val address = addressList!![0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()


                    Log.i("location", "Location: ${it.latitude}, ${it.longitude}")
                    val latlng = LatLng(it.latitude, it.longitude)
                    val marker = MarkerOptions()
                        .position(latlng)
                        .title(binding.etPlace.text.toString())
                    mMap.addMarker(marker)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                    Log.i("location", "Location: ${binding.etPlace.text.toString()}")
                    val geo = Geofence.Builder()
                        .setRequestId("Geo${id++}")
                        .setCircularRegion(it.latitude, it.longitude, 100F)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .build()

                    val geoRequest = GeofencingRequest.Builder()
                        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                        .addGeofence(geo)
                        .build()
                    val geoPendingIntent = PendingIntent.getBroadcast(
                        this,
                        id,
                        Intent(this, GeoReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    Log.i("mapsActivity", "id ${id}")
                    geoClient.addGeofences(geoRequest, geoPendingIntent).addOnSuccessListener {
                        Log.i("geofenceAdded", "geofence added successful")
                    }.addOnFailureListener{
                        Log.i("geofenceAdded", "geofence failed")
                        Log.i("failed", it.message!!)
                    }
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        viewModel = PlaceViewModel(application, FirebaseFirestore.getInstance(), userUid)
        mMap = googleMap
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
//            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {


        } else {
            Log.i("perms", "request perms")
            requestPermissions(perms, 0)
        }
        var id = 10000
        val geoClient = LocationServices.getGeofencingClient(this)
        mMap.isMyLocationEnabled = true
        viewModel.places.observe(this, Observer {
            it.let { a ->
                a.forEach {
                    if (it.isFav == true) {
                        val latlng = LatLng(it.latitude, it.longitude)
                        val marker = MarkerOptions()
                            .position(latlng)
                            .title(it.name + " - " + it.description)
                        mMap.addMarker(marker)
                        val geo = Geofence.Builder()
                            .setRequestId("Geo${id++}")
                            .setCircularRegion(it.latitude, it.longitude, 100F)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .build()

                        val geoRequest = GeofencingRequest.Builder()
                            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                            .addGeofence(geo)
                            .build()
                        val geoPendingIntent = PendingIntent.getBroadcast(
                            this,
                            id,
                            Intent(this, GeoReceiver::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        Log.i("mapsActivity", "id ${id}")
                        geoClient.addGeofences(geoRequest, geoPendingIntent).addOnSuccessListener {
                            Log.i("geofenceAdded", "geofence added successful")
                        }.addOnFailureListener {
                            Log.i("geofenceAdded", "geofence failed")
                            Log.i("failed", it.message!!)
                        }
                    }
                }

            }
        })


    }
}