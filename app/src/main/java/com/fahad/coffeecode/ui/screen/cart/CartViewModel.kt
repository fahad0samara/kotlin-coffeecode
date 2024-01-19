package com.fahad.coffeecode.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.data.local.entities.CardItem
import com.fahad.coffeecode.data.local.repository.CardRepository
import com.fahad.coffeecode.domain.model.CoffeeDrink

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val itemRepository: CardRepository) : ViewModel() {
  val cart: Flow<List<CardItem>> = itemRepository.getAllItems()

  val groupedItems = cart.map { items ->
    items.groupBy { it.name }
  }

  fun addToCart(item: CoffeeDrink) {
    viewModelScope.launch {
      val newItem = CardItem(
        name = item.name,
        description = item.description,
        imageUri = item.imageUri,
        servingSize = item.servingSize,
        price = item.price
      )

      itemRepository.insertItem(newItem)
    }
  }




  fun deleteItem(item: CardItem) {
    viewModelScope.launch {
      itemRepository.deleteItem(item)
    }
  }

  fun clearCart() {
    viewModelScope.launch {
      itemRepository.deleteAllItems()
    }
  }

  fun incrementItem(item: CardItem) {
    viewModelScope.launch {
      itemRepository.incrementItemQuantity(item.id)
    }
  }

  fun decrementItem(item: CardItem) {
    viewModelScope.launch {
      itemRepository.decrementItemQuantity(item.id)
    }
  }

}









