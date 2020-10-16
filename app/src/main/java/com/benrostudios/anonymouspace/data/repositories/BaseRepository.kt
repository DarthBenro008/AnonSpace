package com.benrostudios.anonymouspace.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.benrostudios.anonymouspace.data.models.NetworkResult
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException


open class BaseRepository {

    val _networkErrorResolution = MutableLiveData<String>()

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        val result = apiOutput(call, error)
        var output: T? = null
        when (result) {
            is NetworkResult.Success ->
                output = result.output
            is NetworkResult.Error -> {
                Log.d("trugger","hello")
                //_networkErrorResolution.postValue(result.exception)
                //TODO: EventBus.getDefault().post("")
            }
        }
        return output
    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): NetworkResult<T> {
        try {
            val response = call()
            Log.d(TAG, "$response")
            return if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(
                    "Server Down"
                )
            }
        } catch (e: UnknownHostException) {
            Log.e(TAG, "$e")
            return NetworkResult.Error(
                "Unresolved Host"
            )
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "$e")
            return NetworkResult.Error(
                "Socket Timeout"
            )
        } catch (e: Exception) {
            Log.e(TAG, "$e")
            return NetworkResult.Error(
                "Something went wrong due to $e"
            )
        }
    }

    companion object {
        const val TAG = "BaseRepo"
    }
}