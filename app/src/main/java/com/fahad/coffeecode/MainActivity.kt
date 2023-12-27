package com.fahad.coffeecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import com.fahad.coffeecode.ui.theme.CoffeeCodeTheme
import com.fahad.coffeecode.ui.theme.CoffeeViewModel

import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient

@AndroidEntryPoint
class MainActivity : ComponentActivity() {





  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CoffeeCodeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {


          FetchAndDisplayData()


        }
       }
    }
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


