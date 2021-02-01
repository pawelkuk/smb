package com.pjatk.pawelkuklinski.miniprojekt1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.io.IOException

class MediaAppWidget : AppWidgetProvider() {
    lateinit var mp: MediaPlayer
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {


    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val views = RemoteViews(context?.packageName, R.layout.media_app_widget)

        if (intent?.action == context?.getString(R.string.action1)) {
            val cnt = (0..1).random()
            Toast.makeText(context, "Show me another doggy ${cnt}", Toast.LENGTH_SHORT).show()
            if (cnt % 2 == 0) {
                views.setImageViewResource(R.id.ivDogs, R.drawable.golden1)
            }
            else {
                views.setImageViewResource(R.id.ivDogs, R.drawable.golden2)
            }
        }

        if (intent?.action == context?.getString(R.string.action2)) {
            val cnt = (0..1).random()
            var songUri: Uri
            if (cnt %2 == 0){
                songUri = Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3")
            } else {
                songUri = Uri.parse("https://samplelib.com/lib/preview/mp3/sample-3s.mp3")
            }
            mp = MediaPlayer()
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            mp.setAudioAttributes(audioAttributes)
            try {
                mp.setDataSource(context!!, songUri)
            } catch (e: IOException){
                e.printStackTrace()
            }
            mp.prepare()
//            mp.stop()

            mp.start()
        }
        val appWidgetId = intent!!.getIntExtra("appWidgetId", 0)
        AppWidgetManager.getInstance(context).updateAppWidget(
            appWidgetId, views
        )

    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.media_app_widget)
    views.setTextViewText(R.id.tvWidget, widgetText)
    val intentWWW = Intent(Intent.ACTION_VIEW)
    intentWWW.data = Uri.parse("https://www.google.com")
    val pendingWWW = PendingIntent.getActivity(
        context,
        0,
        intentWWW,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val intentAction = Intent(context.getString(R.string.action1))
    intentAction.component = ComponentName(context, MediaAppWidget::class.java)
    intentAction.putExtra("appWidgetId", appWidgetId)
    val pendingAction = PendingIntent.getBroadcast(
        context,
        0,
        intentAction,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btWidget2, pendingAction)

    val intentPlayerAction = Intent(context.getString(R.string.action2))
    intentPlayerAction.component = ComponentName(context, MediaAppWidget::class.java)
    intentPlayerAction.putExtra("appWidgetId", appWidgetId)
    val pendingPlayerAction = PendingIntent.getBroadcast(
        context,
        1,
        intentPlayerAction,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btWidget3, pendingPlayerAction)


    views.setImageViewResource(R.id.ivDogs, R.drawable.golden2)

    views.setOnClickPendingIntent(R.id.btWidget1, pendingWWW)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}