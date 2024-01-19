package com.fahad.coffeecode.ui.screen.Home.search

import AsyncImageProfile
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.collectAsState
import com.fahad.coffeecode.R
import com.fahad.coffeecode.domain.model.CoffeeDrink
import com.fahad.coffeecode.ui.CoffeeViewModel
import com.fahad.coffeecode.ui.screen.cart.CartViewModel
import com.fahad.coffeecode.ui.theme.dimens

@Composable
fun SearchScreen(
  coffeeViewModel: CoffeeViewModel,

  navController: NavController
) {
    var searchText by remember { mutableStateOf("") }
  // Collect coffeeItems from the view model
  val coffeeItems by coffeeViewModel.coffeeItems.collectAsState()

  // Filter coffeeItems based on searchText
  val filteredFood = coffeeItems.filter {
    it.name.contains(searchText, ignoreCase = true)
  }



  Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(top = dimens.medium1)

        // Add padding for status bar
        .systemBarsPadding()
        .padding(bottom = dimens.large)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimens.medium1),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            // Search bar with TextField
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(text = "search for coffee") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimens.medium1),
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,
                        modifier = Modifier
                            .padding(dimens.small2)
                            .size(dimens.medium2),

                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TextFieldDefaults.colors(

                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    disabledPlaceholderColor = Color.Transparent,
                    disabledLeadingIconColor = Color.Transparent,
                    disabledTrailingIconColor = Color.Transparent,
                ),
                shape = CutCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search // Set the keyboard action to Search
                ),
            )
        }
        // Display search results in a LazyColumn when searchText is not empty
        if (searchText.isNotEmpty()) {
            if (filteredFood.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = filteredFood,
                        itemContent = { coffee ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        // Navigate to book details
                                        navController.navigate("details/${coffee.id}")
                                    },

                                ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                  AsyncImageProfile(
                            photoUrl = coffee.imageUri,

                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                      .size(100.dp)
                                      .padding(8.dp),




                                    )
                                    Spacer(modifier = Modifier.width(dimens.medium1))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = coffee.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Price: ${coffee.price}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    )


                }
            } else {
                Text(
                    text ="No results found for $searchText",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.medium1),
                )
            }
        }
    }
}







