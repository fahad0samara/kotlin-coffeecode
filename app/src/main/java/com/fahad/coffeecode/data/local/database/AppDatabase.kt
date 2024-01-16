package com.fahad.coffeecode.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fahad.coffeecode.data.local.dao.FavoriteDao

import RecipeRover.data.local.entities.FavoriteItem
import com.fahad.coffeecode.data.local.dao.CardDao
import com.fahad.coffeecode.data.local.entities.CardItem

@Database(entities = [CardItem::class, FavoriteItem::class], version = 1, exportSchema = false)
 abstract  class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun favoriteDao(): FavoriteDao
}

