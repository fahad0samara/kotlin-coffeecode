package com.fahad.coffeecode.data.local.repository

import com.fahad.coffeecode.data.local.dao.FavoriteDao
import RecipeRover.data.local.entities.FavoriteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoritesDao: FavoriteDao) {

    suspend fun insertFavorite(favorite: FavoriteItem) = favoritesDao.insert(favorite)
    suspend fun deleteFavorite(favorite: FavoriteItem)=favoritesDao.delete(favorite)

    suspend fun deleteAllFavorite()=favoritesDao.deleteAllFavoriteItems()

    fun getAllFavorite(): Flow<List<FavoriteItem>> {
        return favoritesDao.getFavoriteItems()
    }

    fun isCoffeeInFavorites(bookTitle: String): Flow<Boolean> {
        return favoritesDao.isBookInFavorites(bookTitle)
    }


}