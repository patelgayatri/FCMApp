package com.techand.firebaseapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import com.techand.firebaseapp.fcm.FirebaseService
import com.techand.firebaseapp.fcm.NotificationData
import com.techand.firebaseapp.fcm.PushNotification
import com.techand.firebaseapp.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var token = "initialToken"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            token = it.token
        }
        send_btn.setOnClickListener {
            val title = title_txt.text.toString()
            val msg = message_txt.text.toString()
            if (title.isNotEmpty() && msg.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, msg),
                    token
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = RetrofitInstance.api.pushNotification(notification)
                if (response.isSuccessful)
                    Log.e("===", "Response: ${Gson().toJson(response)}")
                else
                    Log.e("===", response.errorBody().toString())

            } catch (e: Exception) {
                Log.e("===", e.toString())
            }
        }
}