package com.fahad.coffeecode.domain.model



import kotlinx.serialization.Serializable
@Serializable
data class CoffeeDrink(
  val id: String,
  val categoryId: String,
  val name: String,
  val description: String,
  val imageUri: String,
  val price: String,
  val ingredients: String,
  val servingSize: String,
  val caffeineContent: String,
  val origin: String,
  val roastLevel: String
)
