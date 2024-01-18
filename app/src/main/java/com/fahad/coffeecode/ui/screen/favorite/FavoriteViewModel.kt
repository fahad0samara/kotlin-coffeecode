package com.fahad.coffeecode.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import RecipeRover.data.local.entities.FavoriteItem
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.fahad.coffeecode.data.local.repository.FavoriteRepository
import com.fahad.coffeecode.domain.model.CoffeeDrink

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import javax.inject.Inject
@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository):ViewModel(){
  val favorite: Flow<List<FavoriteItem>> = favoriteRepository.getAllFavorite()

  // A map to store favorite states for each item
  private val favoriteStates = mutableMapOf<String, MutableState<Boolean>>()

  fun isCoffeeInFavorites(bookTitle: String): Flow<Boolean> {
    return favoriteRepository.isCoffeeInFavorites(bookTitle)
  }

  fun getFavoriteState(name: String): MutableState<Boolean> {
    return favoriteStates.getOrPut(name) { mutableStateOf(false) }
  }

  fun addToFavorite(item: CoffeeDrink, onAddToFavorite: (String) -> Unit) {
    viewModelScope.launch {
      val isItemInFavorites = favoriteRepository.isCoffeeInFavorites(item.name).first()

      if (!isItemInFavorites) {
        val newItem = FavoriteItem(
          name = item.name,
          description = item.description,
          imageUri = item.imageUri,
          servingSize = item.servingSize,
          caffeineContent = item.caffeineContent,
        )
        favoriteRepository.insertFavorite(newItem)

        // Notify the caller that the item was added to favorites
        onAddToFavorite(item.name)
      }

      // Update the favorite state in the shared map
      getFavoriteState(item.name).value = true
    }
  }



  fun deleteFromFavorites(favoriteItem: FavoriteItem) {
    viewModelScope.launch {
      favoriteRepository.deleteFavorite(favoriteItem)
    }
    // Update the favorite state in the shared map
    getFavoriteState(favoriteItem.name).value = false
  }
}
