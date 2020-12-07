package com.pjatk.pawelkuklinski.miniproject2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AddProductReceiver : BroadcastReceiver() {
    private var code = 0

    override fun onReceive(context: Context, intent: Intent) {
        createChannel(context)
        val editProductIntent = Intent( context.getString(R.string.action))
        val id = intent.getLongExtra("id", -1)
        editProductIntent.putExtra("productId", id)

        val pendingIntent = PendingIntent.getActivity(
            context,
            code++,
            editProductIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notification = NotificationCompat.Builder(context, "channelProduct")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Product added:")
            .setContentText(intent.getStringExtra("name"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(code, notification)
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