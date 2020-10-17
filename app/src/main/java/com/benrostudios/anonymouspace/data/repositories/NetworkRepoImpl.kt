package com.benrostudios.anonymouspace.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.anonymouspace.data.models.GenericResponse
import com.benrostudios.anonymouspace.data.models.MessageItem
import com.benrostudios.anonymouspace.data.network.NetworkService
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepoImpl(private val networkService: NetworkService) : NetworkRepo, BaseRepository() {

    private val _roomStatus = MutableLiveData<GenericResponse>()
    private val _userStatus = MutableLiveData<GenericResponse>()
    private val _msgStatus = MutableLiveData<GenericResponse>()
    private val _realtimeMessages = MutableLiveData<List<MessageItem>>()
    private var _messageList: MutableList<MessageItem> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference

    override suspend fun createRoom(location: String, hostId: String): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _roomStatus.postValue(
                safeApiCall(
                    call = { networkService.createRoom(location, hostId) },
                    error = "Unable to create room"
                )
            )
            return@withContext _roomStatus
        }
    }

    override suspend fun joinRoom(chatroomId: String, userId: String): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _roomStatus.postValue(
                safeApiCall(
                    call = { networkService.joinRoom(chatroomId, userId) },
                    error = "Unable to create room"
                )
            )
            return@withContext _roomStatus
        }
    }

    override suspend fun leaveUser(chatroomId: String, userId: String): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _roomStatus.postValue(
                safeApiCall(
                    call = { networkService.leaveRoom(chatroomId, userId) },
                    error = "Unable to create room"
                )
            )
            return@withContext _roomStatus
        }
    }

    override suspend fun addUser(
        uuid: String,
        firstName: String,
        randomImage: String
    ): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _userStatus.postValue(
                safeApiCall(
                    call = { networkService.addUser(uuid, firstName, randomImage) },
                    error = "Unable to create room"
                )
            )
            return@withContext _userStatus
        }
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
        return withContext(Dispatchers.IO) {
            _msgStatus.postValue(
                safeApiCall(
                    call = { networkService.sendMessage(msg, chatroomId, uuid) },
                    error = "Unable to create room"
                )
            )
            return@withContext _msgStatus
        }
    }

    override suspend fun readMessages(chatroomId: String) {
        _messageList = mutableListOf()
        databaseReference = Firebase.database.getReference("Chatroom/$chatroomId/messages")
        val messageListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (messages in snapshot.children) {
                        val message: MessageItem =
                            messages.getValue(MessageItem::class.java) as MessageItem
                        if (!_messageList.contains(message)) {
                            _messageList.add(message)
                        }
                    }
                    _realtimeMessages.postValue(_messageList)
                }
            }

        }
        databaseReference.addValueEventListener(messageListener)
    }

    override val chatroomMessages: LiveData<List<MessageItem>>
        get() = _realtimeMessages
}