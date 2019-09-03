package com.ara.multaka


import android.app.*
import android.app.PendingIntent.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import okhttp3.FormBody
import okhttp3.OkHttpClient

import okhttp3.RequestBody
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.Request







//import sun.jvm.hotspot.utilities.IntArray
// from tutorial : http://www.kotlincodes.com/kotlin/notifications-in-kodlin-oreo-8/

class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        //

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data.get("title"))

            // if there is data or notification the following function will be called weather
            // the App in background or foreground mode.
            issueNotification(remoteMessage)


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow(remoteMessage)
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }//End onMessageRecieve



    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id: String, name: String, importance: Int) {
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true) // set false to disable badges, Oreo exclusive

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    fun issueNotification(remoteMessage: RemoteMessage) {

        // make the channel. The method has been discussed before.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("Ahmad_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT)
        }
        // the check ensures that the channel will only be made
        // if the device is running Android 8+


            // Create an Intent for the activity you want to start
        val resultIntent = Intent(this, NotificationResultActivity::class.java)
        resultIntent.putExtra("post_id",remoteMessage.data.get("post_id"))
        Log.d("post_id",remoteMessage.data.get("post_id"))
            // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }




        val notification = NotificationCompat.Builder(this, "Ahmad_1")
        // the second parameter is the channel id.
        // it should be the same as passed to the makeNotificationChannel() method

        notification
            .setSmallIcon(R.mipmap.ic_launcher) // can use any other icon
            //.setContentTitle("Notification!")
            .setContentTitle(remoteMessage.data.get("title"))
            .setContentText(remoteMessage.data.get("body"))
            .setNumber(3) // this shows a number in the notification dots
            .setContentIntent(resultPendingIntent)
            .setLargeIcon(getBitmapFromURL("http://multaka.ahmadiraq.com/img/multaka-logo.png"))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification.build())
        // it is better to not use 0 as notification id, so used 1.
    }


    private fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }






    private fun handleNow(remoteMessage: RemoteMessage) {

    }

    private fun scheduleJob(remoteMessage: RemoteMessage) {

    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "token is : $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
        //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
    }

    private fun sendRegistrationToServer(token: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val client = OkHttpClient()
        val body = FormBody.Builder()
            .add("token", token)
            .build()

        val request = Request.Builder()
            //.url("http://192.168.0.108:8000/api/fcm")
            .url("http://multaka.ahmadiraq.com/api/fcm")
            .post(body)
            .build()

        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }



}
