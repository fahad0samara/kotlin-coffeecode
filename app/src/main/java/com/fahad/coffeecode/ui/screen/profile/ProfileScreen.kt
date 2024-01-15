package com.fahad.coffeecode.ui.screen.profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.fahad.coffeecode.ui.screen.profile.ActionButton.ActionButton


@Composable
fun ProfileScreen(
  navController: NavController, userDataViewModel: UserDataViewModel

) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(48.dp))

      Image(
        painter = rememberAsyncImagePainter(
          ImageRequest.Builder(LocalContext.current).data(data = userDataViewModel.photoUrl).apply {
            crossfade(true)
            transformations(CircleCropTransformation())
          }.build()
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(96.dp)
          .clip(CircleShape)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = userDataViewModel.displayName,
        fontSize = 24.sp
      )
    }

    Text(
      text = userDataViewModel.email,
      fontSize = 24.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Sign Out Button
    ActionButton(
      text = "Sign Out",

      responseState = userDataViewModel.signOutResponse,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        userDataViewModel = userDataViewModel,
        navController = navController
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Revoke Access Button
    ActionButton(
      text = "Revoke Access",

      responseState = userDataViewModel.revokeAccessResponse,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      userDataViewModel = userDataViewModel,
        navController = navController
    )

    Spacer(modifier = Modifier.height(16.dp))
  }
}







