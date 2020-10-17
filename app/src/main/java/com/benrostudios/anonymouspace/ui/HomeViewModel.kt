package com.benrostudios.anonymouspace.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.anonymouspace.data.models.GenericResponse
import com.benrostudios.anonymouspace.data.repositories.NetworkRepo

class HomeViewModel(private val networkRepo: NetworkRepo) : ViewModel() {

    val realtimeMessages
        get() = networkRepo.chatroomMessages


    suspend fun addUser(
        uuid: String,
        firstName: String,
        randomImage: String
    ): LiveData<GenericResponse> {
        return networkRepo.addUser(uuid, firstName, randomImage)
    }

    suspend fun sendMessage(
        msg: String,
        chatroomId: String,
        uuid: String
    ): LiveData<GenericResponse> {
        return networkRepo.sendMessage(msg, chatroomId, uuid)
    }

    suspend fun readMessages(chatroomId: String) {
        networkRepo.readMessages(chatroomId)
    }

    suspend fun createRoom(location: String, hostId: String): LiveData<GenericResponse> {
        return networkRepo.createRoom(location, hostId)
    }

    suspend fun joinRoom(chatroomId: String, userId: String): LiveData<GenericResponse> {
        return networkRepo.joinRoom(chatroomId, userId)
    }

    suspend fun leaveUser(chatroomId: String, userId: String): LiveData<GenericResponse> {
        return networkRepo.leaveUser(chatroomId, userId)
    }

}