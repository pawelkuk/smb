package com.pjatk.pawelkuklinski.miniprojekt1

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class PlaceViewModel(
    application: Application, private val db: FirebaseFirestore, private val uid:String?
    ): AndroidViewModel(application) {
    var places: MutableLiveData<ArrayList<Place>> = MutableLiveData()
    var editPlace: MutableLiveData<Place> = MutableLiveData()
    var newPlace: MutableLiveData<Place> = MutableLiveData()

    init {
        db.collection("place$uid").addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null){
                val documents = snapshot.documents
                val allPlaces = ArrayList<Place>()
                documents.forEach{
                    val data = it.data
                    val place = Place(id=it.reference.id,
                        name = (data?.get("name") as String?)!!,
                        description = (data?.get("description") as String?)!!,
                        radius = (data?.get("radius") as Long?)!!,
                        isFav = (data?.get("isFav") as Boolean?)!!,
                        longitude = (data?.get("longitude") as Double?)!!,
                        latitude = (data?.get("latitude") as Double?)!!
                    )
                    allPlaces.add(place)
                }
                places.value = allPlaces
            }
        }
    }

    fun add(place: Place) {
        db.collection("place$uid").add(place.toMap()).addOnSuccessListener {
            Log.i(TAG, "Added $place")
            place.id = it.id
            newPlace.value = place
        }
        .addOnFailureListener{
            Log.w(TAG, "Could not add $place")
        }

    }
    fun edit(place: Place){
        val placeIdRef: DocumentReference = db.collection("place$uid").document(place.id!!)
        placeIdRef.update(place.toMap()).addOnCompleteListener{
            if (it.isSuccessful){
                Log.i(
                    TAG, "Edited $place"
                )
            }
            else {
                Log.w(TAG, "Could not edit")
            }
        }
    }

    fun remove(place: Place){
        val placeIdRef: DocumentReference = db.collection("place$uid").document(place.id!!)
        placeIdRef.delete().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(
                    TAG, "Removed $place"
                )
            }else {
                Log.w(TAG, "Could not remove")
            }
        }
    }


    fun removeAll() {
        db.collection("place$uid").get().addOnCompleteListener{
            if (it.isSuccessful){
                val batch = db.batch()
                it.result!!.forEach { ref -> batch.delete(ref.reference) }
                batch.commit()
            }
        }
    }

    fun getPlaceById(id: String) {
        db.collection("place$uid").document(id).get().addOnCompleteListener{
            if (it.isSuccessful){
                val data = it.result!!
                val place = Place(id=data.id,
                    name = (data.get("name") as String?)!!,
                    radius = (data.get("radius") as Long?)!!,
                    description = (data.get("description") as String?)!!,
                    isFav = (data.get("isFav") as Boolean?)!!,
                    longitude = (data.get("longitude") as Double?)!!,
                    latitude = (data.get("latitude") as Double?)!!
                )
                editPlace.value = place
                Log.i(TAG, "editPlace changed")
            }
            else {
                Log.w(TAG, "Could not change editPlace")
            }

        }

    }

}