package com.benrostudios.anonymouspace.utils

import android.util.Log

object Constants {
    const val BASE_URL = "https://anonymouspace.herokuapp.com/"
}

fun decryptor(chat: List<String>): String {
    var result = ""
    chat.forEach { msg ->
        result += decrypt(msg,7)
        Log.d("verynice","$msg $result")
        result += " "
    }
    return result
}

fun decrypt(cipher: String, shift: Int): StringBuffer? {
    val result = StringBuffer()
    for (i in cipher.indices) {
        if (Character.isUpperCase(cipher[i])) {
            val ch = ((cipher[i].toInt() +
                    shift - 65) % 26 + 65).toChar()
            result.append(ch)
        } else {
            val ch = ((cipher[i].toInt() +
                    shift - 97) % 26 + 97).toChar()
            result.append(ch)
        }
    }
    return result
}