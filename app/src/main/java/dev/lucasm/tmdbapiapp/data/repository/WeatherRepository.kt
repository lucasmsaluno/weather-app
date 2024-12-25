package dev.lucasm.tmdbapiapp.data.repository

import dev.lucasm.tmdbapiapp.data.model.WeatherResponse
import dev.lucasm.tmdbapiapp.data.network.WeatherApi
import dev.lucasm.tmdbapiapp.utils.ResultState
import javax.inject.Inject

class WeatherRepository @Inject constructor (
    private val weatherApi: WeatherApi
) {

    suspend fun getWeather(city: String, apiKey: String = "5d7110880d1590b4758eea2b62382a8b"): ResultState<WeatherResponse> {
        return try {
            val response = weatherApi.getWeather(city, apiKey)
            ResultState.Success(response)

        } catch (e: Exception) {
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Failed To Connect To Server"
                is java.net.UnknownHostException -> "No Internet Connection"
                else -> "Unknown Error"
            }
            ResultState.Error(errorMessage)
        }
    }
}