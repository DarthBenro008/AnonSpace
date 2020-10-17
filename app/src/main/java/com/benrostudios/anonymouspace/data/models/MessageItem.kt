package com.benrostudios.anonymouspace.data.models

import java.sql.Timestamp

data class MessageItem(
        val content: String = "",
        val timestamp: Long = 0,
        val userid: String = "",
        val displayname: String = ""
)