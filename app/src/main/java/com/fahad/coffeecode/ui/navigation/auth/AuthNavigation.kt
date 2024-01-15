package com.fahad.coffeecode.ui.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.fahad.coffeecode.ui.screen.login.LoginScreen
import com.fahad.myapplication.ui.screen.login.LoginViewModel

fun NavGraphBuilder.authNavigation(
  navController: NavHostController,
  loginViewModel: LoginViewModel,

  ) {

  navigation(
    startDestination = "login",
    route = "auth",


    )

  {

    composable("login") {
      LoginScreen(
        navController = navController, loginViewModel = loginViewModel
      )
    }

  }
}




