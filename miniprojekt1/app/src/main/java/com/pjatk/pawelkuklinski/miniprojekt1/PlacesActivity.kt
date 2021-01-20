package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityPlacesBinding
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ActivityProductsBinding
import java.io.IOException

class PlacesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("filename", Context.MODE_PRIVATE)
        val uid = intent.getStringExtra("userUid")
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()
        if (sp.getBoolean("isIrritationMode", false)) {
            binding.root.setBackgroundColor(Color.CYAN)
            binding.button.setBackgroundColor(Color.YELLOW)
            binding.btMainMenu.setBackgroundColor(Color.YELLOW)
        }
        val geoCoder = Geocoder(this)

        val viewModel = PlaceViewModel(application, FirebaseFirestore.getInstance(), uid)
        val adapter = PlacesAdapter(viewModel, this, uid)
        viewModel.places.observe(this, Observer {
            it.let {
                adapter.setListPlace(it)
            }
        })

        val btColor = sp.getString("color", null)
        if (btColor != null && !sp.getBoolean("isIrritationMode", false)){
            binding.button.setBackgroundColor(COLOR_MAPPER[btColor]!!)
            binding.btMainMenu.setBackgroundColor(COLOR_MAPPER[btColor]!!)
        }
        binding.rvPlaceList.layoutManager = LinearLayoutManager(this)
        binding.rvPlaceList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvPlaceList.adapter = adapter
        binding.button.setOnClickListener{
            var addressList: List<Address>? = null
            val location = binding.etName.text.toString()
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()


            val place = Place(
                name = binding.etName.text.toString(),
                description = binding.etDescription.text.toString(),
                radius = binding.etRadius.text.toString().toLong(),
                isFav = false,
                id = null,
                latitude = address.latitude,
                longitude = address.longitude
            )
            viewModel.add(place)

        }
        binding.button.setOnLongClickListener {
            viewModel.removeAll()
            true
        }
        binding.btMainMenu.setOnClickListener {
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            mainMenuIntent.putExtra("userUid", uid)
            startActivity(mainMenuIntent)
        }
    }
}