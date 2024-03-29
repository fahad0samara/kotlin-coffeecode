package com.fahad.myapplication.ui.screen.login

import android.app.Activity.RESULT_OK
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.domain.model.Response
import com.fahad.coffeecode.domain.repository.AuthRepository
import com.fahad.coffeecode.domain.repository.OneTapSignInResponse
import com.fahad.coffeecode.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient

import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val repo: AuthRepository,
  val oneTapClient: SignInClient
) : ViewModel() {
  // Expose whether the user is authenticated in Firebase
  val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase

  // Mutable state variables for one-tap sign-in and sign-in with Google responses
  var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
    private set
  var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(Response.Success(false))
    private set
  var isLoading by mutableStateOf(false)
    private set

    // Mutable state variable for sign-in error
    var signInError by mutableStateOf<String?>(null)


  // Coroutine function to perform one-tap sign-in
  fun oneTapSignIn() = viewModelScope.launch {
    isLoading = true
    oneTapSignInResponse = Response.Loading
    oneTapSignInResponse = repo.oneTapSignInWithGoogle()
    isLoading = false
  }

  // Coroutine function to perform sign-in with Google
  fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
    isLoading = true
    signInWithGoogleResponse = Response.Loading
    signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    isLoading = false
  }

  // Function to handle OneTapSignInResponse

  fun handleOneTapSignInResponse(onLaunch: (BeginSignInResult) -> Unit) {
    when (val response = oneTapSignInResponse) {
      is Response.Success -> response.data?.let { onLaunch(it) }
      is Response.Failure -> {
        // Set the error message to be displayed in the UI
        signInError = "One-tap sign-in failed: ${response.e.message}"
        // Reset loading state
        isLoading = false
      }
      is Response.Loading -> {
        // Handle loading state if needed
        isLoading = true
      }
    }
  }

  // Function to handle SignInWithGoogleResponse
  fun handleSignInWithGoogleResponse(onSuccess: (Boolean) -> Unit) {
    when (val response = signInWithGoogleResponse) {
      is Response.Success -> response.data?.let { onSuccess(it) }
      is Response.Failure -> {
        // Set the error message to be displayed in the UI
        signInError = "Sign-in with Google failed: ${response.e.message}"
        // Reset loading state
        isLoading = false
      }
      is Response.Loading -> {
        // Handle loading state if needed
        isLoading = true
      }
    }
  }

  // Function to handle the result of the sign-in launcher
  fun handleSignInResult(result: ActivityResult) {
    if (result.resultCode == RESULT_OK) {
      try {
        val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
        val googleIdToken = credentials.googleIdToken
        val googleCredentials = getCredential(googleIdToken, null)
        signInWithGoogle(googleCredentials)
      } catch (it: ApiException) {
        signInError = "Sign-in failed: ${it.message}"
        isLoading = false
      }
    }
  }
}