package dev.lucasm.tmdbapiapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.lucasm.tmdbapiapp.R
import dev.lucasm.tmdbapiapp.ui.components.SearchBar
import dev.lucasm.tmdbapiapp.utils.ResultState
import dev.lucasm.tmdbapiapp.utils.getDayAndHour
import dev.lucasm.tmdbapiapp.utils.getWeatherIcon
import dev.lucasm.tmdbapiapp.viewmodels.WeatherViewModel

@SuppressLint("DefaultLocale")
@Composable
fun WeatherScreen (
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE5903A),
                    Color(0xFF552a91),
                    Color(0xFF000000)
                ),
                startY = 0f,
                endY = 2000f //
            )
        ),
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp, horizontal = 20.dp)
        ) {
            SearchBar()

            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = uiState.value.resultState) {
                    ResultState.Initial -> {
                        Text(
                            text = "Waiting for Search",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    is ResultState.Loading -> CircularProgressIndicator()
                    is ResultState.Success -> {

                        Text("${state.data.name}, ${state.data.sys.country}", color = Color.White)

                        Spacer(modifier = Modifier.size(20.dp))

                        Image(
                            painter = painterResource(getWeatherIcon(state.data.weather[0].id)),
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                            text = "${(state.data.main.temp - 273.15).toInt().toString()} ºC",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp
                        )

                        Text(
                            text = state.data.weather[0].description
                                .split(" ")
                                .map{ it.capitalize() }
                                .joinToString(" "),
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = getDayAndHour(),
                            color = Color.LightGray,
                            fontSize = 14.sp,
                        )

                        Spacer(modifier = Modifier.size(50.dp))

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.low_temp),
                                    contentDescription = null,
                                    modifier = Modifier.size(75.dp)
                                )
                                Column {
                                    Text(
                                        "Temp Min",
                                        color = Color.Gray
                                    )
                                    Text(
                                        "${(state.data.main.temp_min - 273.15).toInt().toString()} ºC",
                                        color = Color.White
                                    )
                                }
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.high_temp),
                                    contentDescription = null,
                                    modifier = Modifier.size(75.dp)
                                )
                                Column {
                                    Text(
                                        "Temp Max",
                                        color = Color.Gray
                                    )
                                    Text(
                                        "${(state.data.main.temp_max - 273.15).toInt().toString()} ºC",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    is ResultState.Error -> Text(
                        text = state.message ?: "Erro Desconhecido",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}