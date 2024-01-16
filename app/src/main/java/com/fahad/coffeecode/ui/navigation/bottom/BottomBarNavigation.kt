package com.fahad.coffeecode.ui.navigation.bottom


import androidx.compose.runtime.Composable

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import android.annotation.SuppressLint
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fahad.coffeecode.ui.screen.favorite.FavoriteViewModel
import com.fahad.coffeecode.ui.screen.Home.Home
import com.fahad.coffeecode.ui.screen.favorite.FavoriteScreen

import com.fahad.coffeecode.ui.screen.profile.ProfileScreen
import com.fahad.coffeecode.ui.screen.profile.UserDataViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BottomBarNavigation(
  navController: NavHostController,
) {
  val userDataViewModel: UserDataViewModel = hiltViewModel()
  val favoriteViewModel: FavoriteViewModel = hiltViewModel()





  NavHost(
    navController = navController, route = "root", startDestination = BottomBar.Home.route
  ) {
    composable(route = BottomBar.Home.route) {
      Home(
     userDataViewModel
      )
    }

    composable(route = BottomBar.Favorite.route) {
      FavoriteScreen(
        favoriteViewModel, navController

      )
    }







    composable(route = BottomBar.Profile.route) {
      ProfileScreen(
        navController = navController, userDataViewModel = userDataViewModel
      )
    }





    searchNavGraph(navController = navController)

  }
}

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {

  navigation(
    startDestination = "search",
    route = "search_nav_graph",

    ) {
//    composable(route = "search") {
//      SearchScreen(navController = navController, viewModel = hiltViewModel())
//
//    }

  }
}




