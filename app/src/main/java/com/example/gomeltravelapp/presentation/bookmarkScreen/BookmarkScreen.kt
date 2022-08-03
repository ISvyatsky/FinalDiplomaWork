package com.example.gomeltravelapp.presentation.bookmarkScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.components.EmptyView
import com.example.gomeltravelapp.presentation.components.verticalGradient
import com.example.gomeltravelapp.presentation.ui.theme.blue

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BookMark(
    navController: NavController,
    viewModel: BookmarkViewModel/*,
    onDetails: (String) -> Unit*/
) {

    val places by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible =!places.isEmpty) {
            Column {
                BookmarkAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                ) { navController.navigate(Screen.Dashboard.route) }
                Spacer(modifier = Modifier.height(14.dp))
                BookmarkContent(navController, viewModel)
            }
        }
    }
}

@Composable
fun BookmarkAppBar(modifier: Modifier, onBackPressed: () -> Unit) {
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
            text = stringResource(id = R.string.header_title_bookmark),
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkContent(
    navController: NavController,
    viewModel: BookmarkViewModel
) {
    val places by viewModel.state.collectAsState()

    LazyVerticalGrid(cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
    ) {
        items(places.places.distinct()) { place ->
            BookmarkItem(navController, place, /*onDetails = onDetails*/) {
                viewModel.deletePlace(place)
            }
        }
    }
    AnimatedVisibility(visible = places.isEmpty) {
        EmptyView(
            descText = stringResource(id = R.string.book_mark_empty)
        )
    }
}
