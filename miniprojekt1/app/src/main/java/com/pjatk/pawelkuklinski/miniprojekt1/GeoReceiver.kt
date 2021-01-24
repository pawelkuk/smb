package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("geofence", "receiver works")
        val place = intent.getStringExtra("place")
        val event = GeofencingEvent.fromIntent(intent)
        for(geo in event.triggeringGeofences) {
            Log.i("geofence", geo.requestId)

            Toast.makeText(context, "You entered ${place}. Geofence with id: ${geo.requestId}", Toast.LENGTH_LONG).show()
        }
    }
}