package com.fahad.coffeecode.ui.screen.favorite

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import RecipeRover.data.local.entities.FavoriteItem
import androidx.compose.ui.layout.ContentScale
import com.fahad.coffeecode.ui.theme.dimens

@Composable
fun FavoriteScreen(
  viewModel: FavoriteViewModel, navController: NavController,
) {
  val favoriteItems by viewModel.favorite.collectAsState(emptyList())

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Favorite Items", fontSize = 24.sp, fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (favoriteItems.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(dimens.medium1),
        contentAlignment = Alignment.Center
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.medium1),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "No Favorite Items Found",

            fontSize = MaterialTheme.typography.titleMedium.fontSize, fontWeight = FontWeight.Bold
          )
          Text(
            text = "Add Items to your favorite list",

            fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold
          )

        }

      }
    } else {
      LazyColumn {
        items(favoriteItems) { item ->
          FavoriteItemCard(item = item,
            navController = navController,
            onDeleteClick = { viewModel.deleteFromFavorites(item) })
        }
      }
    }
  }
}

@Composable
fun FavoriteItemCard(
  item: FavoriteItem, onDeleteClick: () -> Unit, navController: NavController,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp, pressedElevation = 8.dp, disabledElevation = 0.dp
    )

  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {

        AsyncImageProfile(
          item.imageUri,
          modifier = Modifier
            .size(80.dp)
            .clip(shape = CircleShape),
          contentScale = ContentScale.Crop,
          loadingModifier = Modifier
            .size(80.dp)
            .clip(shape = CircleShape),
          loadingSize = 80,
          loadingColor = MaterialTheme.colorScheme.primary

        )




        Spacer(modifier = Modifier.width(16.dp))

        Column(
          modifier = Modifier.weight(1f)
        ) {
          Text(
            text = item.name, fontSize = 18.sp, fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "${item.servingSize}$", fontSize = 18.sp, fontWeight = FontWeight.Bold
          )
        }

        IconButton(
          onClick = onDeleteClick, modifier = Modifier

            .clip(shape = CircleShape)
            .background(Color.Red)
            .size(40.dp)
        ) {
          Icon(
            Icons.Default.Delete, contentDescription = "Delete", tint = Color.White
          )

        }
      }
    }
  }
}


