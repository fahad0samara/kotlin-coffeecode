package com.fahad.coffeecode


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.fahad.coffeecode.model.CoffeeDrink
import com.fahad.coffeecode.ui.theme.CoffeeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation

@Composable
fun FetchAndDisplayData() {
  val viewModel: CoffeeViewModel = viewModel()
  val coffeeItems by viewModel.coffeeItems.collectAsState()
  val isLoading by viewModel.isLoading.collectAsState()
  val error by viewModel.error.collectAsState()

  // State variable to track the selected category
  var selectedCategory by remember { mutableStateOf("Espresso")}

  // Filtered coffee items based on the selected category
  val filteredCoffeeItems = if (selectedCategory.isNotEmpty()) {
    coffeeItems.filter { it.categoryId == selectedCategory }
  } else {
    coffeeItems
  }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {
    Column {
      // LazyRow for displaying categories
      LazyRow {
        items(coffeeItems.distinctBy { it.categoryId })

          { category ->
          CategoryItem(category.categoryId, selectedCategory == category.categoryId) {
            // Update the selected category when clicked
            selectedCategory = category.categoryId
          }
        }
      }

      if (isLoading) {
        // Show a loading indicator
        CircularProgressIndicator(
          modifier = Modifier
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
        )
      } else {
        // Use LazyColumn to display the filtered list of items
        LazyColumn {
          items(filteredCoffeeItems) { coffeeItem ->
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
              // Display the image using Coil
              AsyncImageProfile(photoUrl = coffeeItem.imageUri)

              Spacer(modifier = Modifier.height(8.dp))

              // Display other text information
              Text(text = "Title: ${coffeeItem.name}", fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = "Description: ${coffeeItem.description}")
              // Add more text fields for other details if needed
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
          }
        }
      }

      if (error.isNotEmpty()) {
        // Show an error message
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
}

// Category item composable
@Composable
fun CategoryItem(categoryId: String, isSelected: Boolean, onClick: () -> Unit) {
  Text(
    text = categoryId,
    modifier = Modifier
      .clickable { onClick() }
      .padding(8.dp)
      .background(if (isSelected) Color.Gray else Color.Transparent)
  )
}


@Composable
fun FetchAndDisplayData1() {
//  val viewModel =hiltViewModel<CoffeeViewModel>()
  val viewModel: CoffeeViewModel = viewModel()
  val coffeeItems by viewModel.coffeeItems1.collectAsState()
  val isLoading by viewModel.isLoading1.collectAsState()
  val error by viewModel.error1.collectAsState()

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {
    if (isLoading) {
      // Show a loading indicator
      CircularProgressIndicator(
        modifier = Modifier
          .padding(16.dp)
          .wrapContentWidth(Alignment.CenterHorizontally)
          .wrapContentHeight(Alignment.CenterVertically)
      )
    } else {
      // Use LazyColumn to display the list of items
      LazyColumn {
        items(coffeeItems) { coffeeItem ->
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            // Display the image using Coil
            AsyncImageProfile(photoUrl = coffeeItem.image)


            Spacer(modifier = Modifier.height(8.dp))

            // Display other text information
            Text(text = "Title: ${coffeeItem.title}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description: ${coffeeItem.description}")
            // Add more text fields for other details if needed
          }
          Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
      }
    }

    if (error.isNotEmpty()) {
      // Show an error message
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
fun AsyncImageProfile(photoUrl: String?) {
  val painter = rememberAsyncImagePainter(
    ImageRequest.Builder(LocalContext.current).data(data = photoUrl).apply(block = fun ImageRequest.Builder.() {
      crossfade(true)
      transformations(CircleCropTransformation())
      scale(Scale.FILL)
    }).build()
  )

  Box(
    modifier = Modifier
      .size(100.dp)
      .clip(CircleShape)
      .background(MaterialTheme.colorScheme.primary)
      .border(2.dp, Color.White, CircleShape)
      .padding(5.dp)
  ) {
    Image(
      painter = painter,
      contentDescription = "User Image",
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )

    // Show loading indicator while the image is loading
    if (painter.state is AsyncImagePainter.State.Loading) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Gray)
      ) {
        CircularProgressIndicator(
          modifier = Modifier
            .size(50.dp)
            .align(Alignment.Center),
          color = Color.White
        )
      }
    }
  }
}






