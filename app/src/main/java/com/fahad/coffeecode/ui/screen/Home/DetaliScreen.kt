package com.fahad.coffeecode.ui.screen.Home

import AsyncImageProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fahad.coffeecode.domain.model.CoffeeDrink
import com.fahad.coffeecode.ui.CoffeeViewModel

@Composable
fun DetailsScreen(coffeeItem: CoffeeDrink) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = { /* Handle back button click */ }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
      }
      Text(
        text = coffeeItem.name,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .weight(1f)
          .wrapContentWidth(Alignment.CenterHorizontally)
      )
      IconButton(onClick = { /* Handle favorite button click */ }) {
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
      }
    }

    // Image
    // Image
    AsyncImageProfile(
      photoUrl = coffeeItem.imageUri,
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
      contentScale = ContentScale.Crop,
      loadingModifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
      loadingSize = 48,
      loadingColor = Color.Gray
    )

    // Rounded background for details
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(
          color = Color.White,
          shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
    ) {
      // Details
      DetailItem("Description", coffeeItem.description)
      DetailItem("Price", coffeeItem.price)
      DetailItem("Ingredients", coffeeItem.ingredients)
      DetailItem("Serving Size", coffeeItem.servingSize)
    }

    // Footer
    Spacer(modifier = Modifier.weight(1f))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = { /* Handle add to cart button click */ },
        modifier = Modifier.weight(1f)
      ) {
        Text(text = "Add to Cart")
      }
      Button(
        onClick = { /* Handle buy now button click */ },
        modifier = Modifier
          .padding(start = 8.dp)
          .weight(1f),
        colors = ButtonDefaults.buttonColors()
      ) {
        Text(text = "Buy Now", color = Color.White)
      }
    }
  }
}

@Composable
private fun DetailItem(label: String, value: String) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
  ) {
    Text(
      text = label,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      color = Color.Gray
    )
    Text(
      text = value,
      fontSize = 16.sp,
      modifier = Modifier.padding(top = 4.dp)
    )
  }
}

