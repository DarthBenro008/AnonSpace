package com.benrostudios.anonymouspace.data.models

sealed class NetworkResult<out T: Any>{
    data class Success<out T : Any>(val output : T) : NetworkResult<T>()
    data class Error(val exception: String)  : NetworkResult<Nothing>()
}