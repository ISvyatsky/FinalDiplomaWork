package com.example.gomeltravelapp.presentation.mapScreen

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gomeltravelapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    navController: NavController,
) {
    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    PermissionRequired(
        permissionState = locationPermission,
        permissionNotGrantedContent = {
            LocationNoGranted {
                locationPermission.launchPermissionRequest()
            }
        },
        permissionNotAvailableContent = {}
    ) {
        MapViewContent(navController, mapViewModel/*, placeViewModel*/)
    }
}

@Composable
fun LocationNoGranted(
    onButtonClicked: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title, Button) = createRefs()
        Text(
            text = stringResource(id = R.string.location_needed),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 32.dp)
                .constrainAs(title) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                }
        )

        androidx.compose.material.Button(
            onClick = { onButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 32.dp)
                .constrainAs(Button) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                    )
                    top.linkTo(title.bottom)
                }
        ) {
            Text(text = stringResource(id = R.string.request_permission))
        }
    }
}

@Preview
@Composable
fun PreviewLocationNoGranted() {
    LocationNoGranted {}
}
