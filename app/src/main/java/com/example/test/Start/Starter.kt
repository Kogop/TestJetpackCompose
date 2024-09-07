package com.example.test.Start

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.test.Start.MainScreen
//import com.example.test.Start.ViewModelForStarterScreen

@Composable
fun StartPoint() {
    var viewModel: ViewModelForStarterScreen = viewModel()


    // display the user interface
//    MainScreen(
//        isFahrenheit = viewModel.isFahrenheit,
//        result = viewModel.convertedTemp,
//        convertTemp = {viewModel.calculateConversion(it)},
//        toggleSwitch = {viewModel.doSwitchToggle()}
//    )
    StarterScreen()
}


@Composable
fun StarterScreen() {


    Column {
        Text(text = "Welcome to This App")
    }
}