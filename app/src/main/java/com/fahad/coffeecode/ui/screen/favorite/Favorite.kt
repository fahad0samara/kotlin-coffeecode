package com.fahad.kotlin_auth_with_googles.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun FavoriteScreen(  navController: NavHostController,

                    ) {

  Box(
    modifier = Modifier
      .fillMaxSize()

  ) {
    Text(
      text = "Favorite Screen",
      modifier = Modifier.align(Alignment.Center)
    )
  }


}