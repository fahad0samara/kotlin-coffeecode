package com.fahad.coffeecode

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InternetStatusScreen() {
  var isInternetAvailable by remember { mutableStateOf(true) }
  val context = LocalContext.current

  // Check internet status when the composable is initially composed
  DisposableEffect(context) {
    isInternetAvailable = checkInternetConnection(context)

    // Show a toast message based on internet status
    val message = if (isInternetAvailable) {
      "Connected to internet"
    } else {
      "No internet connection"
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    // Dispose the effect when the composable is no longer in the composition
    onDispose { }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    InternetNotAvailableContent()

  }
}



@Composable
fun InternetNotAvailableContent() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.no), // Replace with your image resource
      contentDescription = null,
      modifier = Modifier.size(120.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Oops! No internet connection",
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
      color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "Please check your internet connection and try again.",
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}



@SuppressLint("ServiceCast")
fun checkInternetConnection(context: Context): Boolean {
  val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val network = connectivityManager.activeNetwork
  val capabilities = connectivityManager.getNetworkCapabilities(network)
  return capabilities != null && (
    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    )
}

fun isInternetAvailable(context: Context): Boolean {
  val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val network = connectivityManager.activeNetwork
  val capabilities = connectivityManager.getNetworkCapabilities(network)
  return capabilities != null && (
    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
      capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    )
}

