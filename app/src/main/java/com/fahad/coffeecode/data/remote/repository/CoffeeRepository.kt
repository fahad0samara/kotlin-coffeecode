package com.fahad.coffeecode.data.remote.repository
import com.fahad.coffeecode.data.remote.service.CoffeeService
import com.fahad.coffeecode.model.CoffeeDrink
import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeRepository @Inject constructor(
                                              private val coffeeService: CoffeeService
  ) {

  suspend fun getCoffeeDrinkList(): List<CoffeeDrink> {
    return coffeeService.getCoffeeDrinkList()
  }



}
















