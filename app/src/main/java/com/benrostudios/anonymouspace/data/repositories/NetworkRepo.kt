package com.benrostudios.anonymouspace.data.repositories

import androidx.lifecycle.LiveData
import com.benrostudios.anonymouspace.data.models.GenericResponse
import com.benrostudios.anonymouspace.data.models.MessageItem

interface NetworkRepo {
    suspend fun createRoom(location: String, hostId: String): LiveData<GenericResponse>
    suspend fun joinRoom(chatroomId: String, userId: String): LiveData<GenericResponse>
    suspend fun leaveUser(chatroomId: String, userId: String): LiveData<GenericResponse>
    suspend fun addUser(uuid: String, firstName: String, randomImage: String): LiveData<GenericResponse>
    suspend fun updateScreenTime(uuid: String, screenTime: Int): LiveData<GenericResponse>
    suspend fun sendMessage(msg: String, chatroomId: String, uuid: String): LiveData<GenericResponse>
    suspend fun readMessages(chatroomId: String)
    val chatroomMessages: LiveData<List<MessageItem>>
}