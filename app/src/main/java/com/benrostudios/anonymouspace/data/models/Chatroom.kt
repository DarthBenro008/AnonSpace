package com.benrostudios.anonymouspace.data.models

data class Chatroom (
    val chatCount: Int,
    val key: String,
    val location: String,
    val messages: List<MessageItem>,
    val users: List<String>
    )