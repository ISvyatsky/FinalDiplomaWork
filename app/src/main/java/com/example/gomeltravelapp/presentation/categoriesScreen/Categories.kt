package com.example.gomeltravelapp.presentation.categoriesScreen

import  androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.domain.model.TypePlace
import com.example.gomeltravelapp.presentation.LoadingContent
import com.example.gomeltravelapp.presentation.PlaceViewModel
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.components.verticalGradient
import com.example.gomeltravelapp.presentation.ui.theme.blue

@Composable
fun Categories(
    navController: NavController,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val hasError by viewModel.hasError.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val placeCategoryItemList = listOf(
        TypePlace("Архитектура (здания, сооружения)",viewState.architecturePlaces.size, R.raw.establishment, viewState.architecturePlaces),
        TypePlace("Исторические места",viewState.historicPlaces.size, R.raw.library, viewState.historicPlaces),
        TypePlace("Городская среда (площади, фонтаны)",viewState.urbanEnvironmentPlaces.size, R.raw.park, viewState.urbanEnvironmentPlaces),
        TypePlace("Музеи",viewState.museumPlaces.size, R.raw.museum, viewState.museumPlaces),
        TypePlace("Театральные развлечения",viewState.entertainmentPlaces.size, R.raw.amusementpark, viewState.entertainmentPlaces),
        TypePlace("Религия (церкви, монастыри, храмы)",viewState.religionPlaces.size, R.raw.church, viewState.religionPlaces),
        TypePlace("Все объекты",viewState.allPlaces.size, R.raw.tourattraction, viewState.allPlaces)
    )

    LoadingContent(loading) {
        Surface(
            modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = !hasError) {
                Column {
                    CategoriesAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) { navController.navigate(Screen.Dashboard.route) }
                    Spacer(modifier = Modifier.height(14.dp))
                    DailyPlaceCategoryContent(navController, placeCategoryItemList)
                }
            }
        }
    }
}

@Composable
fun DailyPlaceCategoryContent(
    navController: NavController,
    placeCategories: List<TypePlace>
) {
    if (placeCategories.isNotEmpty()) {
        PlaceCategoryContent(navController, placeCategories)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaceCategoryContent(
    navController: NavController,
    placeCategories: List<TypePlace>
) {
    LazyVerticalGrid(cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
    ) {
        items(placeCategories) { placeCategory ->
            CategoryItem(
                navController, placeCategory)
        }
    }
}

@Composable
fun CategoriesAppBar(modifier: Modifier, onBackPressed: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        RecipeGradient(
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
    }
}

@Composable
fun RecipeGradient(modifier: Modifier) {
    Spacer(
        modifier = modifier.verticalGradient()
    )
}

