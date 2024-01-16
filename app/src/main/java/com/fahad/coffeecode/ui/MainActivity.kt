package com.fahad.coffeecode.ui

import android.graphics.Color

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.fahad.coffeecode.InternetStatusScreen
import com.fahad.coffeecode.isInternetAvailable
import com.fahad.coffeecode.ui.screen.Home.Home
import com.fahad.coffeecode.ui.navigation.RootNavigation

import com.fahad.coffeecode.ui.theme.CoffeeCodeTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.light(
        Color.TRANSPARENT, Color.TRANSPARENT
      ),
      navigationBarStyle = SystemBarStyle.light(
        Color.TRANSPARENT, Color.TRANSPARENT
      )

    )

    setContent {
      CoffeeCodeTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
            AppContent()

        }
      }
    }
  }
}

@Composable
fun AppContent() {
  if (isInternetAvailable(
        context = LocalContext.current
        )
  ) {
    RootNavigation(navController = rememberNavController())

  } else {
    InternetStatusScreen()
  }
}



//@Composable
//fun FetchAndDisplayData1() {
//  // State to hold the fetched data
//  var todoItem by remember { mutableStateOf(listOf<CoffeeDrink>()) }
//
//  // Create a MutableState to hold the loading state
//  var loading by remember { mutableStateOf(true) }
//
//  // Create a MutableState to hold any potential error
//  var error by remember { mutableStateOf<String?>(null) }
//
//
//
//  // Use the LaunchedEffect to launch the coroutine when the component is first composed
//  LaunchedEffect(true) {
//    // Create HTTP client
//    val client = HttpClient(CIO)
//
//    try {
//      // Make a GET request
//      val response: HttpResponse = client.get("https://coofee.azurewebsites.net/api/coffee-items")
//
//      // Parse JSON response into TodoItem data class
//      val items = Json.decodeFromString<List<CoffeeDrink>>(response.bodyAsText())
//
//      // Update the state with the fetched data
//      todoItem = items
//      loading = false
//    } catch (e: Exception) {
//      // Handle errors
//      error = "Error: ${e.message}"
//      loading = false
//    } finally {
//      // Close the HTTP client
//      client.close()
//    }
//  }
//
//  // Display the fetched data in the UI
//  if (loading) {
//    // Show a loading indicator
//    CircularProgressIndicator()
//  } else if (error != null) {
//    // Show an error message
//    Text(text = error!!)
//  } else if (todoItem.isNotEmpty()) {
//    // Use a LazyColumn to display the list of items
//    LazyColumn {
//      items(todoItem) { todoItem ->
//        // Use Text() or other Compose components to display the relevant information for each item
//        Text(text = "Title: ${todoItem.name}\nCompleted: ${todoItem.description}")
//      }
//    }
//  }
//}


