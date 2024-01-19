package com.fahad.coffeecode.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fahad.coffeecode.data.local.entities.CardItem

import kotlinx.coroutines.flow.Flow

@Dao

interface CardDao {
    @Insert
    suspend fun insert(item: CardItem)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<CardItem>>

    @Query("SELECT  name FROM items")
    suspend fun getAllItemNames(): List<String>

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: Long): CardItem

    @Delete
    suspend fun delete(item: CardItem)

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()

    @Query("UPDATE items SET quantity = quantity + 1 WHERE id = :itemId")
    suspend fun incrementItemQuantity(itemId: Long)

    @Query("UPDATE items SET quantity = quantity - 1 WHERE id = :itemId")
    suspend fun decrementItemQuantity(itemId: Long)



}

