package com.fahad.coffeecode.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoffeApi(
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("ingredients")
    val ingredients: List<String>,
    @SerialName("title")
    val title: String
)