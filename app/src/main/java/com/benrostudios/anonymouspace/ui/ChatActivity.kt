package com.benrostudios.anonymouspace.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.adapters.ChatAdapter
import com.benrostudios.anonymouspace.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ChatActivity : AppCompatActivity() {

    private val sharedPrefManager: SharedPrefManager by inject()
    private val viewModel: HomeViewModel by inject()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chat_recycler.layoutManager = LinearLayoutManager(this)
        listenMessages()
        message_edit_text_layout.setEndIconOnClickListener {
            if (usr_message_text.text.toString().isNotEmpty()) {
                sendMessage(usr_message_text.text.toString())
            }
        }
        readMessages()
        leave_room_text.setOnClickListener {
            leaveRoom()
        }
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
                    adapter = ChatAdapter(it, sharedPrefManager.uuid)
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
                        if (it.message) {
                            sharedPrefManager.currentChatRoomId = "none"
                            quiteActivity()
                        }
                    }
                }
        }
    }

    private fun quiteActivity() {
        finish()
    }
}