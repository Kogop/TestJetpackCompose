package com.example.test.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed interface NetworkInterface {

    data object  Available: NetworkInterface
    data object  UnAvailable: NetworkInterface
}

private fun networkCallback(callback:(NetworkInterface) -> Unit): ConnectivityManager.NetworkCallback =
    object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
//            super.onAvailable(network)
            callback(NetworkInterface.Available)
        }

        override fun onLost(network: Network) {
//            super.onLost(network)
            callback(NetworkInterface.UnAvailable)
        }
    }

fun getNetworkConnectionState(connectivityManager: ConnectivityManager) : NetworkInterface {

    val network = connectivityManager.activeNetwork

    val isConnected = connectivityManager.getNetworkCapabilities(network)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false

    return (if (isConnected) NetworkInterface.Available else NetworkInterface.UnAvailable)

}

fun Context.observeConnectivityAsFlow(): Flow<NetworkInterface> = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState ->
        trySend(connectionState)
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getNetworkConnectionState(connectivityManager)
    trySend(currentState)

    awaitClose(){
        connectivityManager.unregisterNetworkCallback(callback)
    }

}

val Context.currentConnectivityState: NetworkInterface

    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getNetworkConnectionState(connectivityManager)
    }


@Composable
fun rememberConnectivityState(): State<NetworkInterface> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect {
            value = it
        }
    }

}

@Composable
fun NetworkScreen(){
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkInterface.Available
        }
    }

    Column {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = if(isConnected) "Connected" else "Unavailable")
        }
    }

}



