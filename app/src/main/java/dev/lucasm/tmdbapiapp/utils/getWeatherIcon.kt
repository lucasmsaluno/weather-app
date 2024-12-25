package dev.lucasm.tmdbapiapp.utils

import dev.lucasm.tmdbapiapp.R

fun getWeatherIcon(weatherCode: Int): Int {
    return when (weatherCode) {
        in 200..299 -> R.drawable.thunder_cloud
        in 300..399 -> R.drawable.rainy_cloud
        in 500..599 -> R.drawable.rainy_cloud_stronger
        in 600..699 -> R.drawable.snow_cloud
        800 -> R.drawable.sun
        801, 802 -> R.drawable.sunny_cloud
        in 803..899 -> R.drawable.cloud
        else -> R.drawable.atm
    }
}