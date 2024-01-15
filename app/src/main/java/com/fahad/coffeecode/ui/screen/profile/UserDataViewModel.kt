package com.fahad.coffeecode.ui.screen.profile

import androidx.compose.runtime.State


import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.coffeecode.domain.model.Response
import com.fahad.coffeecode.domain.repository.ProfileRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


import javax.inject.Inject
@HiltViewModel
class UserDataViewModel @Inject constructor(
  private val repo: ProfileRepository
) : ViewModel() {

  // Exposing properties from the repository
  val displayName get() = repo.displayName
  val photoUrl get() = repo.photoUrl
  val email get() = repo.email

  // Mutable state variables for sign-out and revoke access responses
  private val _signOutResponse = mutableStateOf<Response<Boolean>>(Response.Success(false))
  val signOutResponse: State<Response<Boolean>> get() = _signOutResponse

  private val _revokeAccessResponse = mutableStateOf<Response<Boolean>>(Response.Success(false))
  val revokeAccessResponse: State<Response<Boolean>> get() = _revokeAccessResponse

  private val _showLogoutDialog = MutableStateFlow(false)
  val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog

  fun setShowLogoutDialog(show: Boolean) {
    _showLogoutDialog.value = show
  }

  private val _showRevokeAccessDialog = MutableStateFlow(false)
    val showRevokeAccessDialog: StateFlow<Boolean> = _showRevokeAccessDialog

    fun setShowRevokeAccessDialog(show: Boolean) {
      _showRevokeAccessDialog.value = show
    }



  // Coroutine function to perform sign-out operation
  fun signOut() = viewModelScope.launch {
    _signOutResponse.value = Response.Loading



    try {
      _signOutResponse.value = repo.signOut()
    } catch (e: Exception) {
      _signOutResponse.value = Response.Failure(e)
    }
  }

  // Coroutine function to perform revoke access operation
  fun revokeAccess() = viewModelScope.launch {
    _revokeAccessResponse.value = Response.Loading
    try {
      _revokeAccessResponse.value = repo.revokeAccess()
    } catch (e: Exception) {
      _revokeAccessResponse.value = Response.Failure(e)
    }
  }
}

