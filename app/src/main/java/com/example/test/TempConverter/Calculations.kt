package com.example.test.TempConverter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Calc() {
    var viewModel: ViewModelForTempConverter = viewModel()


    // display the user interface
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.convertedTemp,
        convertTemp = {viewModel.calculateConversion(it)},
        toggleSwitch = {viewModel.doSwitchToggle()}
    )
}

@Composable
fun MainScreen(isFahrenheit: Boolean, result: String, convertTemp: (String) -> Unit, toggleSwitch: () -> Unit) {

    var inputTextState by remember { mutableStateOf("") }

    fun onTextChange(newValue: String){
        inputTextState = newValue
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = MaterialTheme.fillMaxSize()

    ){
        Text( "Temp conversion app",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,

            )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 9.dp),
            colors = CardDefaults.cardColors(Color.White),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Switch(checked = isFahrenheit, onCheckedChange = { toggleSwitch()})
                OutlinedTextField(
                    value = inputTextState,
                    onValueChange = { onTextChange(it) },
                    label = { Text(text = "Enter temperature") },
                    modifier = Modifier.padding(16.dp),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    trailingIcon = {
                        Text(text = if (isFahrenheit) "\u2109" else "\u2103")
                    }
                )
            }
        }
        Text( if(result == ""){""} else{  "result = $result"},
            Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,)

        Button(onClick = {convertTemp(inputTextState)}) {
            Text(text =  "Convert to " +  if (isFahrenheit) "Celcius" else "Fahrenheit")
        }
    }
}