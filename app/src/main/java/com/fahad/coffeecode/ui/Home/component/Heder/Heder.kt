package com.fahad.coffeecode.ui.Home.component.Heder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp

@Composable
fun Header() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(6.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    UserName()
    Spacer(modifier = Modifier.height(16.dp))
    SearchHome()
  }
}






