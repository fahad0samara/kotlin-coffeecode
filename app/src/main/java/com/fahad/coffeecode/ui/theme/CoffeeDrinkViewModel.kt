package com.fahad.coffeecode.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.data.CoffeeRepository
import com.fahad.coffeecode.model.CoffeeDrink
import kotlinx.coroutines.launch

class CoffeeViewModel() : ViewModel() {
    private val repository = CoffeeRepository()
  val coffeeDrinkList = mutableStateOf<List<CoffeeDrink>>(emptyList())

  fun fetchCoffeeDrinkList() {
    viewModelScope.launch {
      // Use the repository to get the coffee drink list
      coffeeDrinkList.value = repository.getCoffeeDrinkList()
    }
  }


}
