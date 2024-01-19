package com.fahad.coffeecode.util.Button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedButton(
  roundedCornerSize: Int = 15,
  backgroundTintColor: Color,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit = {},
  onClick: () -> Unit = {}
) {
  Box(
    modifier = modifier
      .shadow(
        elevation = 9.dp,
        shape = RoundedCornerShape(roundedCornerSize.dp)
      )
      .size(50.dp)
      .background(
        color = backgroundTintColor,
      )
      .clickable { onClick() },
    contentAlignment = Alignment.Center
  ) {
    content()
  }
}

@Composable
fun RoundedButton1(
  roundedCornerSize: Int = 15,
  backgroundTintColor: Color,
  modifier: Modifier = Modifier,
  text: String,
  Color: Color,


  onClick: () -> Unit = {}
) {
  Box(
    modifier = modifier
      .shadow(
        elevation = 9.dp,
        shape = RoundedCornerShape(roundedCornerSize.dp),
        ambientColor = Color
      )
      .size(50.dp)
      .background(
        color = backgroundTintColor,
      )
      .clickable { onClick() },
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      fontSize = 20.sp,
      color = Color
    )


  }
}
