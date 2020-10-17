package com.benrostudios.anonymouspace.data.repositories

import androidx.lifecycle.LiveData
import com.benrostudios.anonymouspace.data.models.GenericResponse
import com.benrostudios.anonymouspace.data.models.MessageItem

class NetworkRepoImpl : NetworkRepo {
    override suspend fun createRoom(location: String, hostId: String): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun joinRoom(chatroomId: String, userId: String): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun leaveUser(chatroomId: String, userId: String): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(
        uuid: String,
        firstName: String,
        randomImage: String
    ): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateScreenTime(
        uuid: String,
        screenTime: Int
    ): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(
        msg: String,
        chatroomId: String,
        uuid: String
    ): LiveData<GenericResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun readMessages(chatroomId: String) {
        TODO("Not yet implemented")
    }

    override val chatroomMessages: LiveData<List<MessageItem>>
        get() = TODO("Not yet implemented")
}