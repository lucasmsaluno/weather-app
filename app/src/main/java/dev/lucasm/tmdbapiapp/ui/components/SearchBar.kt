package dev.lucasm.tmdbapiapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.lucasm.tmdbapiapp.viewmodels.WeatherViewModel

@Composable
fun SearchBar (
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    var cityInput by remember { mutableStateOf("") }
    val controller = LocalSoftwareKeyboardController.current

    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (cityInput.isNotEmpty()) Color.White else Color.Transparent,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (cityInput.isNotEmpty()) viewModel.getWeather(cityInput.trimIndent())
                    controller?.hide()
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (cityInput.isEmpty()) {
                        Text(
                            text = "Enter City Name",
                            style = TextStyle(
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 18.sp
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}