package com.benrostudios.anonymouspace.data.network

import android.content.Context
import com.benrostudios.anonymouspace.utils.Constants
import com.benrostudios.anonymouspace.utils.SharedPrefManager
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {


    companion object {
        operator fun invoke(
            context: Context,
            sharedPrefManager: SharedPrefManager
        ): NetworkService {
            val auth_key = sharedPrefManager.jwtStored
            val requestInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization:", auth_key)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
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