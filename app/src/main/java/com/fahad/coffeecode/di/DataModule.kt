package com.fahad.coffeecode.di

import android.content.Context
import androidx.room.Room
import com.fahad.coffeecode.data.local.dao.CardDao
import com.fahad.coffeecode.data.local.dao.FavoriteDao
import com.fahad.coffeecode.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      AppDatabase::class.java,
      "list_coffee_database"
    ).build()
  }

  @Provides
  fun provideItemDao(database: AppDatabase): CardDao = database.cardDao()

  @Provides
  fun provideFavoriteDao(database: AppDatabase): FavoriteDao = database.favoriteDao()


}


