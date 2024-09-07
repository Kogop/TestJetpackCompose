package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.test.TempConverter.ViewModelForTempConverter
import com.example.test.ui.theme.TestTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge() // плохая ломающая хуита, все улетает за экран
        setContent {
            TestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CalcScreen
                ) {
                    composable<CalcScreen>{
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Column {
                                Calc()
                                Button(onClick = { navController.navigate(OtherScreen(2))}) {
                                    Text(text = "Go somewhere else")
                                }
                            }
                        }
                    }
                    composable<OtherScreen> {
                        val args = it.toRoute<OtherScreen>()
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Column {
                                Box(modifier = Modifier){
                                    if(args.id == 2) {
                                        Text(text = "id = ${args.id}")
                                    }
                                }
                                Button(onClick = { }) {
                                    Text(text = "Go somewhere else")
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

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

@Serializable
object CalcScreen

@Serializable
data class OtherScreen(
    val id : Int
)
