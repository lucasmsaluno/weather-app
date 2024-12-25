package dev.lucasm.tmdbapiapp.data.network

import dev.lucasm.tmdbapiapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather (
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}