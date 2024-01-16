package com.fahad.coffeecode.data.local.repository


import com.fahad.coffeecode.data.local.entities.CardItem
import com.fahad.coffeecode.data.local.dao.CardDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardRepository @Inject constructor(private val itemDao: CardDao) {
    suspend fun insertItem(item: CardItem) = itemDao.insert(item)

    suspend fun deleteItem(item: CardItem) = itemDao.delete(item)

    suspend fun deleteAllItems() = itemDao.deleteAllItems()

    fun getAllItems(): Flow<List<CardItem>> {
        return itemDao.getAllItems()
    }

    suspend fun getItemById(itemId: Long) = itemDao.getItemById(itemId)

    suspend fun getAllItemNames(): List<String> {
        return itemDao.getAllItemNames()
    }

    suspend fun incrementItemQuantity(itemId: Long) {
        itemDao.incrementItemQuantity(itemId)
    }

    suspend fun decrementItemQuantity(itemId: Long) {
        itemDao.decrementItemQuantity(itemId)
    }


}
