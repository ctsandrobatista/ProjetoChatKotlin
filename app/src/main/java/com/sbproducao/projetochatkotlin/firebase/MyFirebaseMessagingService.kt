package com.sbproducao.projetochatkotlin.firebase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(ContentValues.TAG, "From: " + remoteMessage.from)
        Log.d(ContentValues.TAG, "Notification Message Body: " + remoteMessage.notification!!.body)
    }
}