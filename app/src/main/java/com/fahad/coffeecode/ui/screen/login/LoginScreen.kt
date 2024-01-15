package com.fahad.coffeecode.ui.screen.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fahad.coffeecode.R

import com.fahad.myapplication.ui.screen.login.LoginViewModel

@Composable
fun LoginScreen(
  loginViewModel: LoginViewModel, navController: NavController
) {
  val launcher = rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
    loginViewModel.handleSignInResult(result)
  }

  val oneTapSignInEffectKey = remember(loginViewModel.oneTapSignInResponse) { Object() }
  LaunchedEffect(oneTapSignInEffectKey) {
    loginViewModel.handleOneTapSignInResponse { result ->
      launcher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
    }
  }

  val signInWithGoogleEffectKey = remember(loginViewModel.signInWithGoogleResponse) { Object() }
  LaunchedEffect(signInWithGoogleEffectKey) {
    loginViewModel.handleSignInWithGoogleResponse { signedIn ->
      if (signedIn) {
        navController.navigate("home")
      }
    }
  }

  Box(
    modifier = Modifier.fillMaxSize()


  ) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
            .fillMaxSize()
            .alpha(0.4f)
        )
    }



    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // App Logo or Image

      // App Title or Text
      Text(
        text ="My Application",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp),
        color = Color.White
      )

      // App Description or Text
      Text(
        text = "This is a sample app to demonstrate how to implement Google Sign-In in Jetpack Compose.",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = Color.White
      )

      Spacer(modifier = Modifier.height(16.dp))

      // SignInButton
      SignInButton(
        onClick = { loginViewModel.oneTapSignIn() },
        isLoading = loginViewModel.isLoading,
        error = loginViewModel.signInError,
        onDismissError = { loginViewModel.signInError = "" }
      )
    }
  }
}

@Composable
fun SignInButton(
  onClick: () -> Unit,
  isLoading: Boolean,
  error: String? = null,
  onDismissError: () -> Unit = {}
) {
  val rememberedError by rememberUpdatedState(newValue = error)

  val buttonModifier = Modifier.systemBarsPadding()
    .fillMaxWidth()
    .padding(bottom = 48.dp)

  Button(
    modifier = buttonModifier,
    shape = RoundedCornerShape(20.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = if (isLoading) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
      contentColor = if (isLoading) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
    ),
    elevation = ButtonDefaults.buttonElevation(
      defaultElevation = 0.dp,
      pressedElevation = 0.dp,
      disabledElevation = 0.dp
    ),
    onClick = { if (!isLoading) onClick() }
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier
          .size(20.dp)
          .padding(end = 8.dp)
      )
    }
    Image(
      painter = painterResource(id = R.drawable.ic_google_logo),
      contentDescription = null,
      modifier = Modifier
        .size(40.dp)
        .padding(end = 8.dp)
    )
    Text(
      text = if (isLoading) "Logging in..." else "Sign in with Google",
      modifier = Modifier.padding(6.dp),
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold
    )
  }

  // Display error message using the ErrorSnackbar composable
  rememberedError?.let {
    ErrorSnackbar(error = it, onDismiss = onDismissError)
  }

}


@Composable
fun ErrorSnackbar(
  error: String,
  onDismiss: () -> Unit
) {
  Snackbar(
    modifier = Modifier.padding(16.dp),
    action = {
      TextButton(onClick = onDismiss) {
        Text("Dismiss")
      }
    }
  ) {
    Text(error, color = Color.White)
  }
}



