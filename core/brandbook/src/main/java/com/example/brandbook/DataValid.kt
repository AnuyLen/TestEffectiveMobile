package com.example.brandbook

import android.text.TextUtils

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
        .matches()
}

fun String.isPasswordValid(checkPassword: String): Boolean {
    return this == checkPassword
}