package com.benrostudios.anonymouspace.data.models

import android.icu.number.NumberFormatter

data class GenericResponse(
    val message: String? = null,
    val chatroomid: String? = null,
    val uuid: String? = null,
    val chatCount: String? = null,
    val displayName: String? = null,
    val firstName: String? = null,
    val location: String? = null,
    val screenTime: Int? = null,
    val randomimage: String? = null,
    val userId: String? = null
)