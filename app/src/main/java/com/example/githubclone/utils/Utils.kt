package com.example.githubclone.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, isLongToast: Boolean = false) {
    Toast.makeText(
        this,
        message,
        if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}