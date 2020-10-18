package com.benrostudios.anonymouspace.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.adapters.ChatAdapter
import com.benrostudios.anonymouspace.utils.NearbyApi
import com.benrostudios.anonymouspace.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ChatActivity : AppCompatActivity() {

    private val sharedPrefManager: SharedPrefManager by inject()
    private val viewModel: HomeViewModel by inject()
    private lateinit var adapter: ChatAdapter
    private lateinit var nearbyApi: NearbyApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        nearbyApi = NearbyApi(this,application){

        }
        val linearLayout = LinearLayoutManager(this)
        linearLayout.reverseLayout = true
        chat_recycler.layoutManager = linearLayout
        listenMessages()
        viewModel.switchChat(false)
        message_edit_text_layout.setEndIconOnClickListener {
            if (usr_message_text.text.toString().isNotEmpty()) {
                sendMessage(usr_message_text.text.toString())
            }
        }
        readMessages()
        leave_room_text.setOnClickListener {
            leaveRoom()
        }
        nearbyApi.sender = true
        nearbyApi.chatroomId = sharedPrefManager.currentChatRoomId
        nearbyApi.advertise()
    }

    override fun onDestroy() {
        nearbyApi.stopNearby()
        super.onDestroy()
    }

    private fun sendMessage(msg: String) {
        lifecycleScope.launch {
            viewModel.sendMessage(
                msg,
                sharedPrefManager.currentChatRoomId,
                sharedPrefManager.uuid
            ).observeForever {
                if (it != null) {
                    if (it.message) {
                        usr_message_text.setText("")
                    }
                }
            }
        }
    }

    private fun readMessages() {
        lifecycleScope.launch {
            viewModel.readMessages(sharedPrefManager.currentChatRoomId)
        }
    }

    private fun listenMessages() {
        lifecycleScope.launch {
            viewModel.realtimeMessages.observeForever {
                if (it != null) {
                    adapter = ChatAdapter(it.asReversed(), sharedPrefManager.uuid)
                    chat_recycler.adapter = adapter
                }
            }
        }
    }

    private fun leaveRoom() {
        lifecycleScope.launch {
            viewModel.leaveUser(sharedPrefManager.currentChatRoomId, sharedPrefManager.uuid)
                .observeForever {
                    if (it != null) {
                        sharedPrefManager.currentChatRoomId = "none"
                        runOnUiThread {
                            startActivity(Intent(this@ChatActivity, Home::class.java))
                            this@ChatActivity.finish()
                        }
                    }
                }
        }
    }
}