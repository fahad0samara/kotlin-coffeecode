package com.fahad.coffeecode.data.remote.repository.data
import com.fahad.coffeecode.data.remote.service.CoffeeService
import com.fahad.coffeecode.domain.model.CoffeeDrink
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
















