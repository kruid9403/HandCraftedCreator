package com.handcraftedcreator.handcraftedcreator.fcm

import com.google.firebase.firestore.SetOptions
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.handcraftedcreator.handcraftedcreator.managers.FirebaseManager

class MyFirebaseMessagingService(): FirebaseMessagingService() {

    private var functions = FirebaseFunctions.getInstance()
    private val firebaseManager = FirebaseManager()


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        firebaseManager.sellerData().set({"fcmToken"}, SetOptions.merge())
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

    }
}

