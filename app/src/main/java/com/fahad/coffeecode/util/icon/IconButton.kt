package com.fahad.coffeecode.util.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun IconButtonWithIcon(
  modifier: Modifier = Modifier,
  iconResourceId: Int,
  onClick: () -> Unit = {}
) {
  IconButton(
    onClick = { onClick() },
    modifier = modifier

  ) {
    Icon(
      painter = painterResource(id = iconResourceId),
      contentDescription = null, // Provide a meaningful content description
      modifier = Modifier
    )
  }
}