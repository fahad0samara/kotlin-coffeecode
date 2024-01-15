package com.fahad.coffeecode.ui.screen.profile.ActionButton
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.fahad.coffeecode.domain.model.Response
import com.fahad.coffeecode.ui.MainActivity
import com.fahad.coffeecode.ui.screen.profile.UserDataViewModel

@Composable
fun ActionButton(
  text: String,
  responseState: State<Response<Boolean>>,
  modifier: Modifier = Modifier,
  navController: NavController,
  userDataViewModel: UserDataViewModel
) {

  // Observe the showLogoutDialog and showRevokeAccessDialog states
  val showLogoutDialog by userDataViewModel.showLogoutDialog.collectAsState()
  val showRevokeAccessDialog by userDataViewModel.showRevokeAccessDialog.collectAsState()

  // Check if the logout or revoke access dialog should be shown
  if (showLogoutDialog || showRevokeAccessDialog) {
    ConfirmationDialog(
      title = if (showLogoutDialog) "Confirm Logout" else "Confirm Revoke Access",
      message = if (showLogoutDialog) "Are you sure you want to sign out?" else "Are you sure you want to revoke access?",
      onConfirm = {
        // Handle the corresponding action based on the dialog type
        if (showLogoutDialog) {
          try {
            userDataViewModel.signOut()

            val intent = Intent(navController.context, MainActivity::class.java)
            ActivityCompat.finishAffinity(navController.context as MainActivity)
            navController.context.startActivity(intent)
          } catch (e: Exception) {
            Log.e("ProfileScreen", "Error logging out: ${e.message}", e)
          }
          userDataViewModel.setShowLogoutDialog(false)
        } else {
          // Handle the revoke access logic here
          try {
            userDataViewModel.revokeAccess()
          } catch (e: Exception) {
            Log.e("ProfileScreen", "Error revoking access: ${e.message}", e)
          }
          userDataViewModel.setShowRevokeAccessDialog(false)
        }
      },
      onDismiss = {
        // Set the state to hide the dialog based on the type
        if (showLogoutDialog) {
          userDataViewModel.setShowLogoutDialog(false)
        } else {
          userDataViewModel.setShowRevokeAccessDialog(false)
        }
      }
    )
  }


  Button(
    onClick = {
      // Show the confirmation dialog based on the button clicked
      if (text == "Sign Out") {
        userDataViewModel.setShowLogoutDialog(true)
      } else if (text == "Revoke Access") {
        userDataViewModel.setShowRevokeAccessDialog(true)
      }
    },
    modifier = modifier
  ) {
    when (responseState.value) {
      is Response.Loading -> {
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          CircularProgressIndicator()
          Text(text = "Loading...")
        }
      }
      is Response.Success -> {
        Text(text = text)
      }
      is Response.Failure -> {
        Text(text = text)
      }
    }
  }
}

@Composable
fun ConfirmationDialog(
  title: String,
  message: String,
  onConfirm: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(title, fontWeight = FontWeight.Bold) },
    text = { Text(message) },
    confirmButton = {
      Button(
        onClick = {
          onConfirm()
          onDismiss()
        }
      ) {
        Text("Yes")
      }
    },
    dismissButton = {
      Button(
        onClick = onDismiss
      ) {
        Text("No")
      }
    }
  )
}
