package com.benrostudios.anonymouspace.utils

import android.util.Log

object Constants {
    const val BASE_URL = "http://34.72.167.147:80/"
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
        if(cipher[i].toInt() in 65..90 || cipher[i].toInt() in 97..122) {
            if (Character.isUpperCase(cipher[i])) {
                val ch = ((cipher[i].toInt() +
                        shift - 65) % 26 + 65).toChar()
                result.append(ch)
            } else {
                val ch = ((cipher[i].toInt() +
                        shift - 97) % 26 + 97).toChar()
                result.append(ch)
            }
        }else{
            result.append(cipher[i])
        }
    }
    return result
}