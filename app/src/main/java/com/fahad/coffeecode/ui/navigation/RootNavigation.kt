package com.fahad.coffeecode.ui.navigation

import com.fahad.coffeecode.ui.navigation.auth.authNavigation
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fahad.coffeecode.R

import com.fahad.coffeecode.ui.navigation.bottom.BottomBarRoot


import com.fahad.myapplication.ui.screen.login.LoginViewModel

@Composable
fun RootNavigation(navController: NavHostController) {
  val loginViewModel: LoginViewModel = hiltViewModel()



  // Check authentication state when the RootNavigationGraph is recomposed
  // Observe changes in authentication state
  LaunchedEffect(loginViewModel.isUserAuthenticated) {
    if (loginViewModel.isUserAuthenticated) {
      navController.navigate("home")
    } else {
      navController.navigate("auth")
    }
  }

  // In RootNavigation composable
  NavHost(
    navController = navController, route = "root"
    , startDestination = "splash"
  ) {
    composable(route = "splash") {
      SplashScreen()
    }
    authNavigation(
        navController = navController,
        loginViewModel = loginViewModel,


    )

    composable(route = "home") {
      BottomBarRoot()
    }
  }




}





  @Composable
  fun SplashScreen() {
    Box(
      modifier = Modifier.fillMaxSize(),

      contentAlignment = Alignment.Center
    ) {
      // Add your splash screen content
      Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
      ) {

        Image(
          painter = painterResource(id = R.drawable.google_logo),

          contentDescription = null,
          alignment = Alignment.Center,
          modifier = Modifier.height(300.dp)

        )

      }
    }

  }





