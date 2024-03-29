package com.fahad.coffeecode.ui.screen.Home.component.item

import AsyncImageProfile
import RecipeRover.data.local.entities.FavoriteItem
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.fahad.coffeecode.domain.model.CoffeeDrink
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fahad.coffeecode.R
import com.fahad.coffeecode.ui.CoffeeViewModel
import com.fahad.coffeecode.ui.screen.favorite.FavoriteViewModel
import com.fahad.coffeecode.util.icon.IconButtonWithIcon
import kotlinx.coroutines.flow.first

@Composable
fun ItemAndCategory(
    navController: NavController
) {
  val viewModel: CoffeeViewModel = hiltViewModel()
  val favoriteViewModel: FavoriteViewModel = hiltViewModel()

  val context = LocalContext.current




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
      contentPadding = PaddingValues(
        horizontal = 16.dp, vertical = 1.dp
      ), horizontalArrangement = Arrangement.spacedBy(8.dp)
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
          navController = navController,


          coffeeItem = item,
          backgroundTint = color,
          onFavoriteClick = { isFavorite ->
            if (isFavorite) {
              favoriteViewModel.addToFavorite(item) { itemName ->
                Toast.makeText(context, "Added to favorites: $itemName", Toast.LENGTH_SHORT).show()
              }

       }
          }



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
fun CategoryItem(
  categoryId: String,
  isSelected: Boolean,
  onClick: () -> Unit,
) {
  Box(modifier = Modifier
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
      width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)
    )
    .padding(8.dp)) {
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
  onFavoriteClick: (Boolean) -> Unit = {},
  favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController

) {


  var isFavorite by remember(coffeeItem.name) {
    favoriteViewModel.getFavoriteState(coffeeItem.name)
  }

  // Load initial favorite state
  LaunchedEffect(key1 = coffeeItem.name) {
    val isItemInFavorites = favoriteViewModel.isCoffeeInFavorites(
      coffeeItem.name
    ).first()
    isFavorite = isItemInFavorites
  }




  Box {

    ElevatedCard(
      modifier = Modifier
        .clickable {
          Log.d("Clickable", "Card clicked for item: ${coffeeItem.id}")
          navController.navigate("details/${coffeeItem.id}")
        }
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
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
      ) {

        IconFavorite(
          modifier = Modifier
            .padding(16.dp)
            .size(35.dp)
            .align(Alignment.Start)
            .background(Color.White, CircleShape),
          isFavorite = isFavorite,
          onClickFavorite = {
            if (!isFavorite) {
              // Call the onFavoriteClick only if the item is not already a favorite
              onFavoriteClick(true)
            }
            // Update the UI state (isFavorite) directly
            isFavorite = true
          }
        )

        Column(modifier = Modifier.padding(start = 2.dp, end = 1.dp)) {
          Text(
            text = coffeeItem.name, fontWeight = FontWeight.Bold,

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

            Text(
              text = coffeeItem.price, fontWeight = FontWeight.Bold,

              modifier = Modifier.padding(end = 4.dp)

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
      circularCrop = true,
    )
  }
}

@Composable
fun IconFavorite(
  modifier: Modifier = Modifier,
  isFavorite: Boolean,
  onClickFavorite: () -> Unit = {}
) {
  Box(
    modifier = modifier.clickable { onClickFavorite() }
  ) {
    val icon = if (isFavorite) {
      Icons.Default.Favorite // Filled heart icon
    } else {
      Icons.Default.FavoriteBorder // Empty heart icon
    }

    Icon(
      imageVector = icon,
      contentDescription = if (isFavorite) "Favorite" else "Not Favorite",
      tint = if (isFavorite) Color.Red else Color.Gray, // Tint color for the icon
      modifier = Modifier.size(33.dp)
    )
  }
}







