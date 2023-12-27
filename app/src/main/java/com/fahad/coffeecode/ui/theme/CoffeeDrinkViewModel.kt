package com.fahad.coffeecode.ui.theme

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.model.CoffeeDrink
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ViewModelScoped
class CoffeeViewModel @Inject constructor() : ViewModel() {
  private val _coffeeItems = MutableStateFlow<List<CoffeeDrink>>(emptyList())
  val coffeeItems: StateFlow<List<CoffeeDrink>> = _coffeeItems.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()



    init {
    fetchData()
    }


  fun fetchData() {
    _isLoading.value = true
    viewModelScope.launch {
      try {

        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://coofee.azurewebsites.net/api/coffee-items")
        val items = Json.decodeFromString<List<CoffeeDrink>>(response.bodyAsText())
        _coffeeItems.value = items

        Log.d("CoffeeViewModel", "Items: $items")


      } catch (e: Exception) {
        _error.value = "Error: ${e.message}"
        Log.e("CoffeeViewModel", "Error: ${e.message}")

      } finally {
        _isLoading.value = false
      }
    }
  }
}





