package com.fahad.coffeecode.ui.screen.Home

import AsyncImageProfile
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.fahad.coffeecode.domain.model.CoffeeDrink
import com.fahad.coffeecode.ui.screen.cart.CartViewModel
import com.fahad.coffeecode.util.Button.RoundedButton
import com.fahad.coffeecode.util.Button.RoundedButton1

@Composable
fun DetailsScreen(coffeeItem: CoffeeDrink,
                  navController: NavHostController,
                  cartViewModel: CartViewModel



                  ) {
    // Check if the item is already in the cart
    val isItemInCart = cartViewModel.isItemInCart(coffeeItem).collectAsState(false).value

  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    // Image and Back button
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
      // AsyncImageProfile with ContentScale.Crop
      AsyncImageProfile(
        photoUrl = coffeeItem.imageUri,
        modifier = Modifier.fillMaxSize(),

        loadingModifier = Modifier.fillMaxSize(),
        loadingSize = 48,
        loadingColor = Color.Gray
      )

      // Back button
      IconButton(
        modifier = Modifier
          .padding(20.dp)
          .size(48.dp)
          .clip(shape = RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.onSecondaryContainer)
          .align(Alignment.TopStart),
        onClick = {
            navController.popBackStack()
        }
      ) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = Icons.Default.ArrowBack,
          contentDescription = "",
          tint = Color.Gray
        )
      }
    }

    // Content
    Column(
      modifier = Modifier
        .fillMaxSize()
        .clip(
          shape = RoundedCornerShape(
            topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp
          )
        )
        .background(MaterialTheme.colorScheme.onSecondaryContainer)
        .padding(24.dp),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Styled Text elements
      StyledText("Name: ${coffeeItem.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
      StyledText("Description: ${coffeeItem.description}")
      StyledText("Price: ${coffeeItem.price}")
      StyledText("Ingredients: ${coffeeItem.ingredients}")
      StyledText("Serving Size: ${coffeeItem.servingSize}")
      StyledText("Caffeine Content: ${coffeeItem.caffeineContent}")
      StyledText("Origin: ${coffeeItem.origin}")
      StyledText("Roast Level: ${coffeeItem.roastLevel}")

      // You can continue adding more details as needed

      Spacer(modifier = Modifier.weight(1f))

      // Add to Cart button
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 44.dp)
      ) {

        RoundedButton1(
          text = if (isItemInCart) "the item is in the cart" else "Add to Cart",




          backgroundTintColor = MaterialTheme.colorScheme.primary,
          onClick = {





            cartViewModel.addToCart(
              coffeeItem,
                context

            )
          },
            Color = Color.White,
            modifier = Modifier
              .fillMaxWidth()
              .padding(0.dp, 0.dp, 0.dp, 16.dp),



        )



      }
    }
  }
}

@Composable
fun StyledText(text: String, fontSize: TextUnit = 16.sp, fontWeight: FontWeight = FontWeight.Normal) {
  Text(
    text = text,
    fontSize = fontSize,
    fontWeight = fontWeight,
    color = MaterialTheme.colorScheme.onPrimary
  )
}


