package com.fahad.coffeecode.data

import android.util.Log
import com.fahad.coffeecode.model.CoffeeDrink
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoffeeRepository @Inject constructor(private val client: HttpClient) {




  suspend fun getCoffeeDrinkList(): List<CoffeeDrink> {
    return withContext(Dispatchers.IO) {
      try {
        // Make a GET request
        val response: HttpResponse = client.get("https://coofee.azurewebsites.net/api/coffee-items")

        // Parse JSON response into a list of CoffeeDrink data class
        val coffeeDrinkList: List<CoffeeDrink> = Json.decodeFromString(response.bodyAsText())
        Log.d("CoffeeRepository", "Coffee list: $coffeeDrinkList")

        // Return the list
        coffeeDrinkList
      } catch (e: Exception) {
        // Handle errors (you might want to throw a custom exception)
        println("Error: ${e.message}")
        Log.e("CoffeeRepository", "Error: ${e.message}")

        emptyList()  // Return an empty list in case of an error
      } finally {
           // Close the HTTP client
            client.close()


      }
    }
  }
}





