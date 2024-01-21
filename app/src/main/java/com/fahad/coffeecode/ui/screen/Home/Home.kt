package com.fahad.coffeecode.ui.screen.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fahad.coffeecode.ui.screen.Home.component.Heder.Header


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fahad.coffeecode.ui.screen.Home.component.item.ItemAndCategory
import com.fahad.coffeecode.ui.screen.profile.UserDataViewModel

@Composable
fun Home(

  userDataViewModel: UserDataViewModel,
    navController: NavController
) {
  LazyColumn(
    contentPadding = PaddingValues(vertical = 2.dp),
    modifier = Modifier.statusBarsPadding()
  ) {
    item {
      Header(navController = navController)
    }
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Explore New Coffees",
          modifier = Modifier
            .padding(6.dp),
          style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "See All",
          modifier = Modifier
            .padding(6.dp)
            .clickable { /* Handle See All click */ },
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
          )
        )
      }
    }
    item {
      ItemAndCategory(
        navController = navController
      )
    }
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Explore Popular Coffees",
          modifier = Modifier
            .padding(6.dp),
          style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "See All",
          modifier = Modifier
            .padding(6.dp)
            .clickable { /* Handle See All click */ },
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
          )
        )
      }
    }

item {
  Column (modifier = Modifier.padding(bottom = 150.dp)){
    CardsSection()
  }

    }


  }
}


