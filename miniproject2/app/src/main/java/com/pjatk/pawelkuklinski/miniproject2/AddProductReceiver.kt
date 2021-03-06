package com.pjatk.pawelkuklinski.miniproject2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AddProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        createChannel(context)
        val serviceIntent = Intent(context, EditProductService::class.java)
        val id = intent.getStringExtra("id")
        Log.i(TAG, "This is the product id $id")
        serviceIntent.putExtra("id", id)
        serviceIntent.putExtra("name" , intent.getStringExtra("name"))
        context.startForegroundService(serviceIntent)
    }

    private fun createChannel(context: Context) {
        val channel = NotificationChannel(
            "channelProduct",
            "Product Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context).createNotificationChannel(channel)

    }
}