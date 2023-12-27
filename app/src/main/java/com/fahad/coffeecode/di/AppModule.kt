package com.fahad.coffeecode.di

import com.fahad.coffeecode.data.CoffeeRepository
import com.fahad.coffeecode.ui.theme.CoffeeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton


import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideHttpClient(): HttpClient {
    return HttpClient(CIO)
  }

  @Provides
  @Singleton
  fun provideCoffeeRepository(client: HttpClient): CoffeeRepository {
    return CoffeeRepository(client)
  }
}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

  @Provides
  @ViewModelScoped
  fun provideCoffeeViewModel(repository: CoffeeRepository): CoffeeViewModel {
    return CoffeeViewModel()
  }
}
