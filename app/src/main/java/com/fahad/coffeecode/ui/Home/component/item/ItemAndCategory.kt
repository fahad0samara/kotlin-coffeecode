package com.fahad.coffeecode.ui.Home.component.item

import AsyncImageProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.fahad.coffeecode.model.CoffeeDrink
import com.fahad.coffeecode.ui.theme.CoffeeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fahad.coffeecode.R
import com.fahad.coffeecode.util.icon.IconButtonWithIcon

@Composable
fun ItemAndCategory() {
  val viewModel: CoffeeViewModel = viewModel()
  val coffeeItems by viewModel.coffeeItems.collectAsState()
  val isLoading by viewModel.isLoading.collectAsState()
  val error by viewModel.error.collectAsState()

  // State variable to track the selected category
  var selectedCategory by remember { mutableStateOf("Espresso") }

  // Filtered coffee items based on the selected category
  val filteredCoffeeItems = coffeeItems.filter { it.categoryId == selectedCategory }


    Column() {
      // LazyRow for displaying categories
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 1
          .dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
        )

      {
        items(coffeeItems.distinctBy { it.categoryId }) { category ->
          CategoryItem(category.categoryId, selectedCategory == category.categoryId) {
            // Update the selected category when clicked
            selectedCategory = category.categoryId
          }
        }
      }

      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(bottom = 32.dp)
      ) {
        itemsIndexed(filteredCoffeeItems) { index, item ->
          val color =
            if (index % 2 == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
          CoffeeItemCard(
            coffeeItem = item,
            backgroundTint = color,
          )
        }
      }

      // Loading indicator
      if (isLoading) {
        CircularProgressIndicator(
          modifier = Modifier
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
        )
      }

      // Error message
      if (error.isNotEmpty()) {
        Text(
          text = "Error: $error",
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
        )
      }
    }
  }


@Composable
fun CoffeeItemCard(coffeeItem: CoffeeDrink) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clickable { /* Handle card click if needed */ },
    shape = RoundedCornerShape(16.dp),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      // Display the image using Coil
      Box(
        modifier = Modifier
          .height(200.dp)
          .fillMaxWidth()
      ) {
        AsyncImageProfile(
          photoUrl = coffeeItem.imageUri,
          modifier = Modifier
            .padding(start = 45.dp, top = 10.dp)
            .size(150.dp),
          contentScale = ContentScale.Fit

        )
      }

      // Content details
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        // Display other text information
        Text(
          text = coffeeItem.name,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = MaterialTheme.colorScheme.primary
        )

        // Additional details (you can customize this part)
        Text(text = "Price: ${coffeeItem.price}", fontSize = 14.sp)
        // Add more details as needed

        // Add an icon for adding to the cart or any other action
        Icon(imageVector = Icons.Default.ShoppingCart,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier
            .size(24.dp)
            .padding(8.dp)
            .clickable { /* Handle icon click if needed */ })
      }
    }
  }
}

@Composable
fun CategoryItem(
  categoryId: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clickable { onClick() }
      .padding(8.dp)
      .background(
        color = if (isSelected) {
          MaterialTheme.colorScheme.primary
        } else {
          Color.Transparent
        }
      )
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(8.dp)
      )
      .padding(8.dp)
  ) {
    Text(
      text = categoryId,
      color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
    )
  }
}



@Composable
fun CoffeeItemCard(
  coffeeItem: CoffeeDrink,
  backgroundTint: Color,
  onClickDonut: (String) -> Unit = {},
  onAddToCart: (CoffeeDrink) -> Unit = {},
) {
  Box {
    ElevatedCard(
      modifier = Modifier
        .clickable { onClickDonut(coffeeItem.name) }
        .width(180.dp)
        .height(270.dp)
        .clip(RoundedCornerShape(20.dp)),
      colors = CardDefaults.cardColors(backgroundTint),
      elevation = CardDefaults.cardElevation(
        defaultElevation = 20.dp,
        pressedElevation = 8.dp,
      ),

    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
      ) {

        IconFavorite(
          modifier = Modifier

            .padding(16.dp)
            .size(20.dp)
            .align(Alignment.Start)
            .background(Color.White, CircleShape)
        )

        Column(modifier = Modifier.padding(start = 2.dp, end = 1.dp)) {
          Text(
            text = coffeeItem.name,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.padding(horizontal = 8.dp)
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            Text(
              text = coffeeItem.caffeineContent,
                fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(end = 4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = coffeeItem.price,
              fontWeight = FontWeight.Bold,

                modifier = Modifier
                    .padding(end = 4.dp)

                )




          }
        }
      }
    }

    AsyncImageProfile(
      photoUrl = coffeeItem.imageUri,
      modifier = Modifier
        .padding(start = 40.dp, top = 15.dp)
        .size(190.dp),
      contentScale = ContentScale.Fit,
      loadingSize = 200,
    )
  }
}


@Composable
fun IconFavorite(modifier: Modifier = Modifier, onClickFavorite: () -> Unit = {}) {
  IconButtonWithIcon(
    modifier = modifier,
    iconResourceId = R.drawable.icon_fav,
    onClick = onClickFavorite
  )
}







//
//@Composable
//fun FetchAndDisplayData1() {
////  val viewModel =hiltViewModel<CoffeeViewModel>()
//  val viewModel: CoffeeViewModel = viewModel()
//  val coffeeItems by viewModel.coffeeItems1.collectAsState()
//  val isLoading by viewModel.isLoading1.collectAsState()
//  val error by viewModel.error1.collectAsState()
//
//  Surface(
//    modifier = Modifier.fillMaxSize(),
//    color = MaterialTheme.colorScheme.background
//  ) {
//    if (isLoading) {
//      // Show a loading indicator
//      CircularProgressIndicator(
//        modifier = Modifier
//          .padding(16.dp)
//          .wrapContentWidth(Alignment.CenterHorizontally)
//          .wrapContentHeight(Alignment.CenterVertically)
//      )
//    } else {
//      // Use LazyColumn to display the list of items
//      LazyColumn {
//        items(coffeeItems) { coffeeItem ->
//          Column(
//            modifier = Modifier
//              .fillMaxWidth()
//              .padding(16.dp)
//          ) {
//            // Display the image using Coil
//            AsyncImageProfile(photoUrl = coffeeItem.image, modifier = Modifier.fillMaxWidth(),
//                contentScale = ContentScale.FillWidth,
//
//            )
//
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Display other text information
//            Text(text = "Title: ${coffeeItem.title}", fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Description: ${coffeeItem.description}")
//            // Add more text fields for other details if needed
//          }
//          Divider(modifier = Modifier.padding(vertical = 8.dp))
//        }
//      }
//    }
//
//    if (error.isNotEmpty()) {
//      // Show an error message
//      Text(
//        text = "Error: $error",
//        modifier = Modifier
//          .padding(16.dp)
//          .fillMaxWidth()
//          .wrapContentHeight(Alignment.CenterVertically)
//      )
//    }
//  }
//}











