package com.fahad.coffeecode.ui.Home

import AsyncImageProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fahad.coffeecode.R
import com.fahad.coffeecode.model.CoffeeDrink
import com.fahad.coffeecode.ui.CoffeeViewModel

import com.fahad.coffeecode.ui.theme.dimens

@Composable
fun CardsSection(
//  recipeViewModel: RecipeViewModel,
//  navController: NavController
) {
  val viewModel: CoffeeViewModel = viewModel()
  val coffeeItems by viewModel.coffeeItems.collectAsState()




  Column {


    LazyRow {
        itemsIndexed(coffeeItems) { index, coffeeItem ->
            CardItem(coffeeItem = coffeeItem)
        }
        }


  }
}

@Composable
fun CardItem(
  coffeeItem: CoffeeDrink,

) {

  Box(
    modifier = Modifier .padding(start = dimens.small2, end = dimens.small1).clickable(onClick = {
//      navController.navigate("itemDetails/${recipe.title}")

    }
    )
      .clip(RoundedCornerShape(25.dp))
      .background(
        color = colorResource(id = R.color.black).copy(alpha = 0.7f)
      )
      .width(dimens.widthImage)
      .height(dimens.heightImage),
    contentAlignment = Alignment.BottomCenter

  ) {
    AsyncImageProfile(
      photoUrl = coffeeItem.imageUri,



      modifier = Modifier
        .fillMaxSize()
        .size(150.dp),
        contentScale = ContentScale.Crop
    )

    Box(
      modifier = Modifier.padding(dimens.small1)



        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color.Transparent,
              Color.Black.copy(alpha = 0.5f),
              Color.Black.copy(alpha = 0.7f),
              Color.Black.copy(alpha = 0.9f),
              Color.Black
            ),
            startY = 0f,
            endY = 50f
          )
        ),


      ) {

      Text(
        text = coffeeItem.name,
        color = Color.White,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        textAlign = TextAlign.Center,

        )

    }

  }
}
