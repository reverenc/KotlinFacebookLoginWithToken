package com.example.kotlinfacebookloginwithtoken

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data != null) sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data["title"]
        val content = data["content"]
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "sana"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "sana Notification", NotificationManager.IMPORTANCE_MIN)
            notificationChannel.description = "sana channel for app test FCM"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setTicker("Sandhya2105")
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("Info")
        notificationManager.notify(999, notificationBuilder.build())
    }

    /*  @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
       Log.d(TAG,"New token: "+s);
        String a=s;
    }*/
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("newToken", s)
        getSharedPreferences("_", Context.MODE_PRIVATE).edit().putString("fb", s).apply()
        storeToken(s)
    }

    fun storeToken(s1: String?) {
        SharedPrefManager.getInstance(applicationContext)?.saveDeviceToken(s1)
    } //    public static String getToken(Context context) {
//
//        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
//    }
}
