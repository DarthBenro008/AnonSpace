package com.benrostudios.anonymouspace.data.network

import android.content.Context
import com.benrostudios.anonymouspace.data.models.GenericResponse
import com.benrostudios.anonymouspace.utils.Constants
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {

    @POST("room/createRoom")
    suspend fun createRoom(
        @Field("location") location: String,
        @Field("hostId") userId: String
    ): Response<GenericResponse>

    @POST("room/joinRoom")
    suspend fun joinRoom(
        @Field("chatroomId") chatroomId: String,
        @Field("userId") userId: String
    ): Response<GenericResponse>

    @POST("room/leaveUser")
    suspend fun leaveRoom(
        @Field("chatroomId") chatroomId: String,
        @Field("userId") userId: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("user/useraccount")
    suspend fun addUser(
        @Field("uuid") uuid: String,
        @Field("firstName") firstName: String,
        @Field("randomimage") randomImage: String
    ): Response<GenericResponse>

    @POST("user/updatescreentime")
    suspend fun updateScreenTime(
        @Field("uuid") uuid: String,
        @Field("screenTime") screenTime: Int
    ): Response<GenericResponse>

    @POST("msg/sendmessage")
    suspend fun sendMessage(
        @Field("msg") msg: String,
        @Field("chatroomid") chatroomId: String,
        @Field("uuid") uuid: String
    ): Response<GenericResponse>


    companion object {
        operator fun invoke(
            context: Context,
        ): NetworkService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(context))
                .build()
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(NetworkService::class.java)
        }
    }
}