package com.example.gomeltravelapp.presentation.placeDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.components.NetworkImage
import com.example.gomeltravelapp.presentation.components.verticalGradient
import com.example.gomeltravelapp.presentation.ui.theme.blue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlaceHeader(
    state: PagerState,
    place: Place,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(blue)
    ) {
        val (image, info, topBar, title) = createRefs()
        val imageUrl =
            remember { mutableStateOf("") }
        HorizontalPager(
            state = state,
            count = place.image.size, modifier = Modifier
                .fillMaxSize()
        ) { page ->

            imageUrl.value = place.image[page]

            NetworkImage(
                url = imageUrl.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(346.dp)
                    .constrainAs(image) {
                        linkTo(
                            start = parent.start,
                            top = parent.top,
                            end = parent.end,
                            bottom = info.top,
                            bottomMargin = (-32).dp
                        )
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        DetailsAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .constrainAs(topBar) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
            onBackPressed = { navController.navigateUp()},
            onMap = { navController.navigate(Screen.MapScreen.route) }
        )

        Text(
            text = place.name,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 100.dp,
                    bottom = 8.dp
                )
                .constrainAs(title) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    bottom.linkTo(info.top)
                }
        )
        Surface(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .constrainAs(info) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(35.dp)
                }
        ) {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .background(MaterialTheme.colors.background)
            )
        }
    }
}

@Composable
fun DetailsAppBar(modifier: Modifier, onBackPressed: () -> Unit, onMap: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        RecipeGradient(modifier = Modifier.fillMaxSize())
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

        IconButton(
            onClick = onMap,
            Modifier.constrainAs(share) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Map,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun RecipeGradient(modifier: Modifier) {
    Spacer(
        modifier = modifier.verticalGradient()
    )
}