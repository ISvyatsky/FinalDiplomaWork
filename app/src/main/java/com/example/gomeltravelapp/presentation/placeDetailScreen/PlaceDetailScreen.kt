package com.example.gomeltravelapp.presentation.placeDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.LoadingContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlaceDetailScreen(
    navController: NavController,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {

    val placeDetail by viewModel.state.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()

    val pagerState = rememberPagerState()

    LoadingContent(isLoading) {
        placeDetail.place.let { place ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background),
                        verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (place != null) {
                        PlaceHeader(pagerState, place, navController)
                    }
                    placeDetail.place?.image?.let {
                        DotsIndicator(
                            totalDots = it.size,
                            selectedIndex = pagerState.currentPage
                        )
                    }
                }
               // item { RecipeDivider() }
                item {
                    if (place != null) {
                        PlaceOptions(place) { place ->
                            viewModel.savePlace(place)
                        }
                    }
                }
                item { RecipeDivider() }
                item {
                    if (place != null) {
                        AdressItem(place)
                    }
                }
                item { RecipeDivider() }
                item {
                    if (place != null) {
                        DetailItem(place)
                    }
                }
            }
            LaunchedEffect(key1 = pagerState.currentPage) {
                delay(3000)
                var newPosition = pagerState.currentPage + 1
                if (newPosition >  placeDetail.place!!.image.size - 1) newPosition = 0
                // scrolling to the new position.
                pagerState.animateScrollToPage(newPosition)
            }
        }
    }
}

@Composable
private fun RecipeDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun AdressItem(
    place: Place
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Адрес: ",
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = place.adress,
                    color = MaterialTheme.colors.onBackground.copy(0.8f)
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    place: Place
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = place.information,
                color = MaterialTheme.colors.onBackground.copy(0.8f)
            )
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}