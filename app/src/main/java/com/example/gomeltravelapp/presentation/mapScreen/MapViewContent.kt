package com.example.gomeltravelapp.presentation.mapScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.usecase.get_map.MapVariant
import com.example.gomeltravelapp.presentation.PlaceViewModel
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.ui.theme.blue
import com.example.gomeltravelapp.presentation.util.bitmapDescriptorFromVector
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class, ExperimentalPagerApi::class)
@Composable
fun MapViewContent(
    navController: NavController,
    viewModel: MapViewModel,
    placeViewModel: PlaceViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val currentLocation by viewModel.locationData.collectAsState()
    val mapVariants by viewModel.mapVariant.collectAsState()
    val location = LatLng(currentLocation.point?.lat ?: 52.4313, currentLocation.point?.long ?: 30.9937)
    var variantsPopupShown by remember { mutableStateOf(false) }

    val places = placeViewModel.placeMapState.places

    if (variantsPopupShown) {
        MapVariantsPopup(
            MapVariant.values().toMutableList(),
            {
                variantsPopupShown = false
            }
        ) {
            variantsPopupShown = false
            viewModel.setMapVariant(it)
        }
    }

    val mapStyleOptions =
        mapVariants?.let { item ->
            MapStyleOptions.loadRawResourceStyle(
                context, item.styleResId
            )
        }

    Scaffold(
        backgroundColor = MaterialTheme.colors.primarySurface,
        topBar = { MapTopBar(
            placeViewModel,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) { navController.navigate(Screen.Dashboard.route) } },
    ) {

        var uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    zoomControlsEnabled = false
                )
            )
        }

        var properties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.NORMAL,
                    minZoomPreference = 12f,
                    maxZoomPreference = 20f,
                    mapStyleOptions = mapStyleOptions
                )
            )
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 4f)
        }

        var currentSelected by remember {
            mutableStateOf<Place?>(null)
        }

        LaunchedEffect(location) {
            snapshotFlow { location }.collectLatest {
                delay(500L)
                uiSettings = uiSettings.copy(myLocationButtonEnabled = true)
                properties = properties.copy(isMyLocationEnabled = true, mapStyleOptions = mapStyleOptions)
                cameraPositionState.move(CameraUpdateFactory.newLatLng(cameraPositionState.position.target))
                viewModel.mapCenterEvent.collect { update ->
                    cameraPositionState.animate(update)
                }
                /*if (cameraPositionState.position.target != location) {
                    location.let {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                it,
                                14f
                            )
                        )
                    }
                }*/
            }
        }
        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    currentSelected = null
                },
            ) {
                places.forEach {
                    Marker(
                        position = LatLng(it.point.lat, it.point.lon),
                        icon = placeViewModel.placeMapState.icon!!.bitmapDescriptorFromVector(context = context),
                        onClick = { _ ->
                            currentSelected = it
                            true
                        }
                    )
                }
            }
            MapTypeControls {
                Timber.tag("GoogleMap").d("Selected map type " + it)
                properties = properties.copy(mapType = it)
            }
            currentSelected?.let {
                PlaceItemCard(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(
                            16.dp, 16.dp, 16.dp, 86.dp
                        ),
                    it,
                    onClickItem = { place ->
                        navController.navigate(Screen.PlaceDetailScreen.route + "?id=${it.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun MapButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(2.dp,2.dp,2.dp,0.dp,)
            .size(50.dp, 30.dp),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(state = ScrollState(0)),
        horizontalArrangement = Arrangement.Center
    ) {
        MapType.values().forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}

@Composable
fun MapTypeButton(type: MapType, onClick: () -> Unit) =
    MapButton(text = type.toString(), onClick = onClick)

@Composable
fun MapTopBar(
    placeViewModel: PlaceViewModel, modifier: Modifier, onBackPressed: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        com.example.gomeltravelapp.presentation.categoriesScreen.RecipeGradient(
            modifier = Modifier.fillMaxSize().background(blue)
        )
        IconButton(
            onClick = onBackPressed,
            Modifier.constrainAs(back) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.header_title_text),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 24.dp, 20.dp, 0.dp)
        )

        IconButton(
            onClick = {
                expanded = true
            },
            Modifier.constrainAs(share) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = "Sort",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        Modifier
            .width(200.dp)
            .background(Color.White, RoundedCornerShape(13.dp))
    ) {
        DropdownMenuItem(
            onClick = { placeViewModel.onEvent(
                MapEvent.OnArchitectureButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "Архитектура")
        }
        DropdownMenuItem(
            onClick = { placeViewModel.onEvent(MapEvent.OnHistoricButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "История")
        }
        DropdownMenuItem(
            onClick =  { placeViewModel.onEvent(MapEvent.OnUrbanButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "Городская среда")
        }
        DropdownMenuItem(
            onClick = { placeViewModel.onEvent(MapEvent.OnMuseumButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "Музеи")
        }
        DropdownMenuItem(
            onClick = { placeViewModel.onEvent(MapEvent.OnReligionButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "Религия")
        }
        DropdownMenuItem(
            onClick = { placeViewModel.onEvent(MapEvent.OnAllButtonClick(placeViewModel.placeMapState.places)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(blue, RoundedCornerShape(13.dp))
        ){
            Text(text = "Все объекты")
        }

    }

    }
}

@Composable
fun MapVariantsPopup(
    variants: MutableList<MapVariant>,
    onDismiss: () -> Unit,
    onVariant: (MapVariant) -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        Box {
            val cornerSize = 13.dp
            Popup(
                alignment = Alignment.BottomEnd,
                onDismissRequest = onDismiss
            ) {
                Box(
                    Modifier
                        .width(200.dp)
                        .padding(20.dp, 95.dp, 0.dp, 0.dp)
                        .background(Color.White, RoundedCornerShape(cornerSize))
                ) {
                    LazyColumn {
                        items(variants) { item ->
                            VariantItem(item) {
                                openDialog.value = false
                                onVariant(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VariantItem(
    mapVariant: MapVariant,
    modifier: Modifier = Modifier,
    onVariant: (MapVariant) -> Unit
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .clickable { onVariant(mapVariant) },
        color = blue,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .semantics {
                    customActions = listOf(
                        CustomAccessibilityAction(
                            label = "",
                            action = { true }
                        )
                    )
                }
        ) {
            Icon(
                painter = painterResource(id = mapVariant.iconResId),
                tint = Color.White,
                contentDescription = stringResource(id = mapVariant.labelResId),
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 4.dp
                    )
            )

            Text(
                text = stringResource(id = mapVariant.labelResId),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 4.dp
                    )
            )
        }
    }
}

