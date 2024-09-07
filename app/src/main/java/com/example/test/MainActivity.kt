package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.test.Network.NetworkScreen
import com.example.test.Start.StartPoint
import com.example.test.Start.StarterScreen
import com.example.test.TempConverter.Calc
import com.example.test.TempConverter.CalcScreen
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
                    startDestination = StarterScreen
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
                                Button(onClick = { navController.navigate(CalcScreen) }) {
                                    Text(text = "Go to Calculator")
                                }
                                NetworkScreen()

                            }
                        }
                    }

                    composable<StarterScreen> {
                        Surface (modifier = Modifier.fillMaxSize()) {
                            Column {
                                StartPoint()
//                                NetworkScreen()
                                Button(onClick = { navController.navigate(CalcScreen) }) {
                                    Text(text = "Go to Calculator")
                                }
                            }

                        }
                    }

                }

            }
        }
    }
}



@Serializable
data class OtherScreen(
    val id : Int?
)

//@Serializable
//data class NetworkScreen


