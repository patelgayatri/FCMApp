package com.techand.firebaseapp.network

import com.techand.firebaseapp.Constants.Companion.CONTENT_TYPE
import com.techand.firebaseapp.Constants.Companion.SERVER_KEY
import com.techand.firebaseapp.fcm.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

   @Headers("Authorization: Key=$SERVER_KEY","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun pushNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}