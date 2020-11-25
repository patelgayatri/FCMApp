package com.techand.firebaseapp.fcm

data class PushNotification(
    val data: NotificationData,
    val to: String
)
