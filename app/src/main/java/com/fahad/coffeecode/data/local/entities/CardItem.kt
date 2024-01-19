package com.fahad.coffeecode.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class CardItem(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val name: String,
  val description: String,
  val imageUri: String,
  val servingSize: String,
  val price: String,
    var quantity: Int = 1,
)





