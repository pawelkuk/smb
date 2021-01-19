package com.pjatk.pawelkuklinski.miniproject4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("geofence", "receiver works")

        val event = GeofencingEvent.fromIntent(intent)
        for(geo in event.triggeringGeofences) {
            Log.i("geofence", geo.requestId)
            Toast.makeText(context, "Geofence with id: ${geo.requestId}", Toast.LENGTH_SHORT).show()
        }
    }
}