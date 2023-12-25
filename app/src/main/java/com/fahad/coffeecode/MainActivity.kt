package com.fahad.coffeecode

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fahad.coffeecode.model.CoffeeDrink
import com.fahad.coffeecode.ui.theme.CoffeeCodeTheme
import com.fahad.coffeecode.ui.theme.CoffeeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class TodoItem(
  val userId: Int,
  val id: Int,
  val title: String,
  val completed: Boolean
)
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CoffeeCodeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val coffeeViewModel: CoffeeViewModel = viewModel()
          // Call the function to perform the network request
          coffeeViewModel.fetchCoffeeDrinkList()

          // Observe the LiveData and display the data in the UI
          CoffeeDrinkList(coffeeViewModel.coffeeDrinkList.value)
          FetchAndDisplayData()
        }
       }
    }
  }
}

@Composable
fun CoffeeDrinkList(coffeeDrinkList: List<CoffeeDrink>) {
  // Display the list of coffee drinks
  LazyColumn {
    items(coffeeDrinkList) { coffeeDrink ->
      // Use Text() or other Compose components to display the relevant information for each item
      Text(text = "Name: ${coffeeDrink.name}\nType: ${coffeeDrink.categoryId}")
    }
  }
}


@Composable
fun FetchAndDisplayData() {
  // State to hold the fetched data
  var todoItem by remember { mutableStateOf<List<CoffeeDrink>>(emptyList()) }

  // Coroutine to make network request on a background thread
  LaunchedEffect(true) {
    // Create HTTP client
    val client = HttpClient(CIO)

    try {
      // Make a GET request
      val response: HttpResponse = client.get("https://coofee.azurewebsites.net/api/coffee-items")

      // Parse JSON response into TodoItem data class
        todoItem = Json.decodeFromString(response.bodyAsText())
    } catch (e: Exception) {
      // Handle errors
      println("Error: ${e.message}")
    } finally {
      // Close the HTTP client
      client.close()
    }
  }

  // Display the fetched data in the UI
  if (todoItem.isNotEmpty()) {
    // Use a LazyColumn to display the list of items
    LazyColumn {
      items(todoItem) { todoItem ->
        // Use Text() or other Compose components to display the relevant information for each item
        Text(text = "Title: ${todoItem.name}\nCompleted: ${todoItem.description}")
      }
    }
  }
}

