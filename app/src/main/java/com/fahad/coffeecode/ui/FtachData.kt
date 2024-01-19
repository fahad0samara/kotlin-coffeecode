package com.fahad.coffeecode.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.data.remote.repository.data.CoffeeRepository
import com.fahad.coffeecode.domain.model.CoffeeDrink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeViewModel  @Inject constructor(
  private val coffeeRepository: CoffeeRepository
) : ViewModel(){
  private val _coffeeItems= MutableStateFlow<List<CoffeeDrink>>(emptyList())

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

          val coffeeDrinkList: List<CoffeeDrink> = coffeeRepository.getCoffeeDrinkList()
            _coffeeItems.value = coffeeDrinkList

        _error.value = ""
        _isLoading.value = false

        } catch (e: Exception) {
            _error.value = e.message ?: "An unexpected error occurred"
            _isLoading.value = false
        } finally {
            _isLoading.value = false

      }

      }
    }



    }










