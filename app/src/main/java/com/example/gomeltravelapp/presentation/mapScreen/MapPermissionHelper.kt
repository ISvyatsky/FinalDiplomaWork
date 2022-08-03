package com.example.gomeltravelapp.presentation.mapScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun MapPermissionHelper(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
    showPermissionPopup: Boolean = false
) {

    /* permission launcher */
    val launcherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onGranted()
        } else onDenied()
    }

    LaunchedEffect(showPermissionPopup) {
        if (showPermissionPopup)
            launcherPermission.launch(permission)
    }
}