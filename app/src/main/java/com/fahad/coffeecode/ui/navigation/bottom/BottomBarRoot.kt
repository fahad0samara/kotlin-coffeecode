

package com.fahad.coffeecode.ui.navigation.bottom

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import com.fahad.coffeecode.ui.screen.cart.CartViewModel
import com.fahad.coffeecode.ui.screen.favorite.FavoriteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarRoot(navController: NavHostController = rememberNavController()

) {
  val favoriteViewModel: FavoriteViewModel = hiltViewModel()
  val cartViewModel:CartViewModel = hiltViewModel()



  Scaffold(
    bottomBar = { BottomBarItem(navController = navController,
        favoriteViewModel = favoriteViewModel,
      cartViewModel = cartViewModel






      ) }
  ) {
    BottomBarNavigation(navController = navController,





      )
  }
}


@Composable
fun BottomBarItem(
  navController: NavHostController,
  favoriteViewModel: FavoriteViewModel,
    cartViewModel: CartViewModel,

) {
  val screens = listOf(
    BottomBar.Home,
    BottomBar.Favorite,
    BottomBar.Cart,
    BottomBar.Profile,

  )

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  val favoriteItems by favoriteViewModel.favorite.collectAsState(emptyList())
  val favoriteItemCount = favoriteItems.size

    val cartItems by cartViewModel.cart.collectAsState(emptyList())
    val cartItemCount = cartItems.size




  val bottomBarDestination = screens.any { it.route == currentDestination?.route }

  if (bottomBarDestination) {
    NavigationBar(
        modifier = Modifier.systemBarsPadding()
          .padding(10.dp).clip(RoundedCornerShape(30.dp),
        ),
      tonalElevation = 20.dp,
        contentColor = MaterialTheme.colorScheme.onSurface,






    ) {

        screens.forEach { screen ->
          NavigationBarItem(
            icon = {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 4.dp)


              ) {
                Icon(
                  imageVector = screen.icon,
                  contentDescription = "Navigation Icon",
                  modifier = Modifier.size(24.dp)
                )
                if (screen.route == BottomBar.Favorite.route) {
                  if (favoriteItemCount > 0) {
                    Badge(count = favoriteItemCount)
                  }
                }
                if (screen.route == BottomBar.Cart.route) {
                  if (cartItemCount > 0) {
                    Badge(count = cartItemCount)
                  }
                }
              }
            },
            label = {
              Text(
                text = screen.title,
                fontWeight = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface,

            ),

            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = {
              navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
              }
            }
          )
        }

    }
  }
}



@Composable
fun Badge(count: Int, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .background(color = Color.Red, shape = CircleShape)
      .padding(4.dp)
  ) {
    Text(
      text = count.toString(),
      color = Color.White,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(2.dp)
    )
  }
}














