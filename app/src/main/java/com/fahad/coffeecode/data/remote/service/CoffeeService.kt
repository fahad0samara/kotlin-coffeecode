package com.fahad.coffeecode.data.remote.service

import android.util.Log
import com.fahad.coffeecode.di.ApiConstants
import com.fahad.coffeecode.domain.model.CoffeeDrink
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

// CoffeeService.kt
class CoffeeService(private val baseUrl: String, private val httpClient: HttpClient) {
    suspend fun getCoffeeDrinkList(): List<CoffeeDrink> {
      try {
        // Make a GET request to the first URL
        val response: HttpResponse = httpClient.get("$baseUrl${ApiConstants.GET_DATA}")

        // Check if the response status is in the success range (200-299)
        if (response.status.isSuccess()) {
          // Parse JSON response into a list of CoffeeDrink data class
          val responseBody = response.bodyAsText()
          Log.d("CoffeeService", "Response body: $responseBody")
          return Json.decodeFromString(responseBody)
        } else {
          Log.e("CoffeeService", "Error with the first URL. Status code: ${response.status}")
        }
      } catch (e: Exception) {
        Log.e("CoffeeService", "Exception with the first URL: ${e.message}")
      }

      // If there was an exception with the first URL or the response status was not successful, try the second URL
      try {
        val secondUrl = "https://coffeecode-e042e-default-rtdb.firebaseio.com/data.json"
        val response: HttpResponse = httpClient.get(secondUrl)

        // Check if the response status is in the success range (200-299)
        if (response.status.isSuccess()) {
          val responseBody = response.bodyAsText()
          Log.d("CoffeeService", "from the second URL: $responseBody")
          return Json.decodeFromString(responseBody)
        } else {
          Log.e("CoffeeService", "Error with the second URL. Status code: ${response.status}")
        }
      } catch (e: Exception) {
        Log.e("CoffeeService", "Exception with the second URL: ${e.message}")
      }

      // If both attempts fail, throw an exception
      throw Exception("Failed to fetch data from both URLs")
    }
  }


