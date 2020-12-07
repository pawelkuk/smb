package com.pjatk.pawelkuklinski.miniproject2

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class EditProductService() : Service() {
    private var code = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ret = super.onStartCommand(intent, flags, startId)
        val editProductIntent = Intent("com.pjatk.pawelkuklinski.miniprojekt1.EditProductsActivity")
        editProductIntent.setPackage("com.pjatk.pawelkuklinski.miniprojekt1")


        val id = intent?.getLongExtra("id", -1)
        editProductIntent.putExtra("productId", id)
        val pendingIntent = PendingIntent.getActivity(
            this,
            code++,
            editProductIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notification = NotificationCompat.Builder(this, "channelProduct")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Product added:")
            .setContentText(intent?.getStringExtra("name"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        startForeground(code, notification)
        NotificationManagerCompat.from(this).notify(code, notification)
        return ret
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}