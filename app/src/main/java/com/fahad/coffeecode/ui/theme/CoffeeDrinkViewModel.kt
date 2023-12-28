package com.fahad.coffeecode.ui.theme

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.model.Coffe
import com.fahad.coffeecode.model.CoffeApi
import com.fahad.coffeecode.model.CoffeeDrink
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class CoffeeViewModel : ViewModel() {
  private val _coffeeItems = MutableStateFlow<List<CoffeeDrink>>(emptyList())
  val coffeeItems: StateFlow<List<CoffeeDrink>> = _coffeeItems.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  private val _error = MutableStateFlow("")
  val error: StateFlow<String> = _error.asStateFlow()
  private val _coffeeItems1 = MutableStateFlow<List<CoffeApi>>(emptyList())
  val coffeeItems1: StateFlow<List<CoffeApi>> = _coffeeItems1.asStateFlow()

  private val _isLoading1 = MutableStateFlow(false)
  val isLoading1: StateFlow<Boolean> = _isLoading1

  private val _error1 = MutableStateFlow("")
  val error1: StateFlow<String> = _error1.asStateFlow()

  init {
    fetchData()
    fetchData1()
  }

  fun fetchData() {
    _isLoading.value = true
    viewModelScope.launch {
      try {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://coofee.azurewebsites.net/api/coffee-items")
        val responseBody = response.bodyAsText()
        Log.d("CoffeeViewModel", "Response body: $responseBody")

        if (response.status.isSuccess()) {
          val coffeeDrinkList: List<CoffeeDrink> = Json.decodeFromString(responseBody)
          Log.d("CoffeeViewModel", "Coffee list: $coffeeDrinkList")
          _coffeeItems.value = coffeeDrinkList
        } else {
          // Handle non-successful response (e.g., show an error message)
          _error.value = "Error: ${response.status.value} - ${response.status.description}"
          Log.e(
            "CoffeeViewModel",
            "Error: ${response.status.value} - ${response.status.description}"
          )
        }
      } catch (e: Exception) {
        _error.value = "Error: ${e.message}"
        Log.e("CoffeeViewModel", "Error: ${e.message}", e)
      } finally {
        _isLoading.value = false
      }
    }
  }

  fun fetchData1() {
    _isLoading1.value = true
    viewModelScope.launch {
      try {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://api.sampleapis.com/coffee/hot")
        val responseBody = response.bodyAsText()
        Log.d("CoffeeViewModel", "Response body: $responseBody")

        if (response.status.isSuccess()) {
          val coffeeDrinkList1: List<CoffeApi> = Json.decodeFromString(responseBody)
          Log.d("CoffeeViewModel", "Coffee list: $coffeeDrinkList1")
          _coffeeItems1.value = coffeeDrinkList1
        } else {
          // Handle non-successful response (e.g., show an error message)
          _error1.value = "Error: ${response.status.value} - ${response.status.description}"
          Log.e(
            "CoffeeViewModel",
            "Error: ${response.status.value} - ${response.status.description}"
          )
        }
      } catch (e: Exception) {
        _error1.value = "Error: ${e.message}"
        Log.e("CoffeeViewModel", "Error: ${e.message}", e)
      } finally {
        _isLoading1.value = false
      }
    }
  }

}




