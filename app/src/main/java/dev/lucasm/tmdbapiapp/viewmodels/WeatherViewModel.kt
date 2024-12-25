package dev.lucasm.tmdbapiapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasm.tmdbapiapp.data.model.WeatherResponse
import dev.lucasm.tmdbapiapp.data.repository.WeatherRepository
import dev.lucasm.tmdbapiapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState<WeatherResponse>())
    val uiState = _uiState.asStateFlow()

    fun getWeather (city: String) {
        viewModelScope.launch {
            val result = weatherRepository.getWeather(city)
            _uiState.value = uiState.value.copy(
                resultState = result
            )
        }
    }
}

data class UiState<T>(
    val resultState: ResultState<T> = ResultState.Initial
)