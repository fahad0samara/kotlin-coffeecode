package com.fahad.coffeecode.di

import com.fahad.coffeecode.data.remote.repository.CoffeeRepository
import com.fahad.coffeecode.data.remote.service.CoffeeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideHttpClient(): HttpClient {
    return HttpClient(CIO) {
      install(ContentNegotiation) {
        json()
      }
    }
  }

  @Provides
  @Singleton
  fun provideCoffeeService(client: HttpClient): CoffeeService {
    return CoffeeService(ApiConstants.COFFEE_SERVICE_BASE_URL, client)
  }

  @Provides
  @Singleton
  fun provideCoffeeRepository( coffeeService: CoffeeService): CoffeeRepository {
    return CoffeeRepository( coffeeService)
  }
}








