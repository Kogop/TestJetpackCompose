package com.example.test.TempConverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelForTempConverter: ViewModel() {

    var isFahrenheit by mutableStateOf(true)

    var convertedTemp by mutableStateOf("")


    fun doSwitchToggle() {
        isFahrenheit = !isFahrenheit
    }


    fun calculateConversion(inputValue: String) {
        try {
            if (isFahrenheit){
                convertedTemp = "%.2f".format((inputValue.toFloat() - 32) * 5 / 9)
                convertedTemp += "\u2103"
            }else{
                convertedTemp = "%.2f".format((inputValue.toFloat() * 9) / 5 + 32)
                convertedTemp += "\u2109"
            }
        }catch (e: Exception){
            convertedTemp = "Invalid input"
        }
    }

}