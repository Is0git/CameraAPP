package com.android.cameraapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

// Date dd-mmm-yyyy format
@SuppressLint("SimpleDateFormat")
fun getCurrentDateInFormat(): String? {
    val date = Calendar.getInstance().time
    return SimpleDateFormat("hh:mm:hh, dd-MMM-yyyy").format(date)
}

fun getCurrentTime(): Long? {
    return System.currentTimeMillis()
}
