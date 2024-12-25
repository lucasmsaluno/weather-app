package dev.lucasm.tmdbapiapp.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun getDayAndHour(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE d - h:mm a", Locale.getDefault())
    return now.format(formatter)
}