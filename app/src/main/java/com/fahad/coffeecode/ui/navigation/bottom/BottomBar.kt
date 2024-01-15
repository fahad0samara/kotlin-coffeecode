package com.fahad.coffeecode.ui.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
  val route: String,
  val title: String,
  val icon: ImageVector
) {
  data object Home : BottomBar(
    route = "home",
    title = "HOME",
    icon = Icons.Default.Home
  )




  data object Favorite : BottomBar(
    route = "FAVORITE",
    title = "FAVORITE",
    icon = Icons.Default.Favorite
  )

  data object Profile : BottomBar(
    route = "PROFILE",
    title = "PROFILE",
    icon = Icons.Default.Person
  )
}