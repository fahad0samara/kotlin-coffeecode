package com.fahad.coffeecode.ui.navigation.bottom


import androidx.compose.runtime.Composable

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import android.annotation.SuppressLint
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fahad.coffeecode.ui.CoffeeViewModel

import com.fahad.coffeecode.ui.screen.Home.DetailsScreen
import com.fahad.coffeecode.ui.screen.favorite.FavoriteViewModel
import com.fahad.coffeecode.ui.screen.Home.Home
import com.fahad.coffeecode.ui.screen.Home.search.SearchScreen
import com.fahad.coffeecode.ui.screen.cart.CartScreen
import com.fahad.coffeecode.ui.screen.cart.CartViewModel
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
    val coffeeViewModel: CoffeeViewModel = hiltViewModel()
  val cartViewModel: CartViewModel = hiltViewModel()







  NavHost(
    navController = navController, route = "root", startDestination = BottomBar.Home.route
  ) {
    composable(route = BottomBar.Home.route) {
      Home(
        userDataViewModel, navController
      )
    }

    composable(route = BottomBar.Favorite.route) {
      FavoriteScreen(
        favoriteViewModel, navController

      )
    }

    composable(route = BottomBar.Cart.route) {
      CartScreen(
        cartViewModel
        )
    }








    composable(
      route = "details/{itemId}",
      arguments = listOf(navArgument("itemId") { type = NavType.StringType })
    ) { backStackEntry ->
      val itemId = backStackEntry.arguments?.getString("itemId")
      val coffeeItem = coffeeViewModel.coffeeItems.value.find { it.id == itemId }
      coffeeItem?.let {
        DetailsScreen(coffeeItem,
            navController = navController,
            cartViewModel = cartViewModel

          )
      }
    }











    composable(route = BottomBar.Profile.route) {
      ProfileScreen(
        navController = navController, userDataViewModel = userDataViewModel
      )
    }





    searchNavGraph(navController = navController, userDataViewModel = userDataViewModel,
        coffeeViewModel = coffeeViewModel
      )

  }
}



fun NavGraphBuilder.searchNavGraph(navController: NavHostController, userDataViewModel: UserDataViewModel,
                                   coffeeViewModel: CoffeeViewModel,
                                   ) {

  navigation(
    startDestination = "search",
    route = "search_nav_graph",

    ) {
    composable(route = "search") {
      SearchScreen(navController = navController, coffeeViewModel = coffeeViewModel,

        )

    }

  }
}




