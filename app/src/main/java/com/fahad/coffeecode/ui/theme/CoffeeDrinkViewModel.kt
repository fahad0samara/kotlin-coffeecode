package com.fahad.coffeecode.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.data.CoffeeRepository
import com.fahad.coffeecode.model.CoffeeDrink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeViewModel @Inject constructor(private val repository: CoffeeRepository) : ViewModel() {

  val coffeeDrinkList = mutableStateOf<List<CoffeeDrink>>(emptyList())
  val isFetching = mutableStateOf(false)

  fun fetchCoffeeDrinkList() {
    viewModelScope.launch {
      try {
        // Set isFetching to true before making the request
        isFetching.value = true


        // Use the repository to get the coffee drink list
        coffeeDrinkList.value = repository.getCoffeeDrinkList()
      } catch (e: Exception) {
        // Handle errors (you might want to throw a custom exception)
        println("Error: ${e.message}")
        Log.e("CoffeeViewModel", "Error: ${e.message}")
        isFetching.value = false
        // Return an empty list in case of an error
        coffeeDrinkList.value = emptyList()
      } finally {
        // Set isFetching to false after the request is completed
        isFetching.value = false
      }
    }
  }
}
