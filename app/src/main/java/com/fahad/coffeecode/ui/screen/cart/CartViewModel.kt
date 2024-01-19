package com.fahad.coffeecode.ui.screen.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.data.local.entities.CardItem
import com.fahad.coffeecode.data.local.repository.CardRepository
import com.fahad.coffeecode.domain.model.CoffeeDrink

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val itemRepository: CardRepository) : ViewModel() {
  val cart: Flow<List<CardItem>> = itemRepository.getAllItems()

  val groupedItems = cart.map { items ->
    items.groupBy { it.name }
  }



  // Check if a CoffeeDrink item is in the cart
  fun isItemInCart(coffeeItem: CoffeeDrink): Flow<Boolean> {
    return cart.map { items ->
      items.any { it.name == coffeeItem.name }
    }
  }

  fun addToCart(item: CoffeeDrink, context: Context) {
    viewModelScope.launch {
      // Check if the item is already in the cart
      val itemInCart = isItemInCart(item).first()

      // Only add the item if it's not already in the cart
      if (!itemInCart) {
        val newItem = CardItem(
          name = item.name,
          description = item.description,
          imageUri = item.imageUri,
          servingSize = item.servingSize,
          price = item.price
        )

        itemRepository.insertItem(newItem)

        // Show a toast indicating that the item is added
        showToast(context, "Item added to cart")
      } else {
        // Show a toast indicating that the item is already in the cart
        showToast(context, "Item is already in the cart")
      }
    }
  }

  private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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









