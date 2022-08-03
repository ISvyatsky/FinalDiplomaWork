package com.example.gomeltravelapp.presentation.placeListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.domain.model.TypePlace
import com.example.gomeltravelapp.presentation.navigation.MainActions
import com.example.gomeltravelapp.presentation.*
import com.example.gomeltravelapp.presentation.components.NetworkImage
import com.example.gomeltravelapp.presentation.components.verticalGradient
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.placeDetailScreen.RecipeGradient
import com.example.gomeltravelapp.presentation.ui.theme.blue
import com.example.gomeltravelapp.presentation.ui.theme.compositedOnSurface

@Composable
fun PlaceListScreen(
    actions: MainActions,
    navController: NavController,
    placeCategories: TypePlace,
    viewModel: PlaceViewModel = hiltViewModel(),
) {

    val searchText = remember {
        mutableStateOf("")
    }
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    val hasError by viewModel.hasError.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LoadingContent(loading) {
        Surface(
            modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = !hasError) {
                Column(modifier = Modifier.fillMaxSize()) {
                    MainAppBar(
                        actions = actions,
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            viewModel.updateSearchTextState(newValue = it)
                            searchText.value = it
                        },
                        onCloseClicked = {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            searchText.value = it
                        },
                        onSearchTriggered = {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                    )

                    ListComponent(
                        navController,
                        searchText.value,
                        placeCategories
                    )
                }
            }
        }
    }
}

@Composable
fun ListComponent(
    navController: NavController,
    searchText: String,
    placeCategories: TypePlace,
    viewModel: PlaceViewModel = hiltViewModel()
) {
    LazyColumn {
        items(
            placeCategories.results.filter {
                it.name.contains(
                    searchText,
                    ignoreCase = true
                )
            }
        ) { placeItem ->
            SearchItem(
                place = placeItem,
                onItemClick = {
                    navController.navigate(Screen.PlaceDetailScreen.route + "?id=${placeItem.id}")
                }
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onBackPressed: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Найти",
                    color = Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black.copy(alpha = ContentAlpha.medium),
                textColor = Color.Black
            )
        )
    }
}

@Suppress("LongParameterList")
@Composable
fun MainAppBar(
    actions: MainActions,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    //onBackPressed: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {

    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            PlaceListAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                onBackPressed = { actions.gotoPlaceCategories() },
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onBackPressed = { actions.gotoPlaceCategories() },
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun PlaceListAppBar(modifier: Modifier, onBackPressed: () -> Unit, onSearchClicked: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        RecipeGradient(modifier = Modifier.fillMaxSize().background(blue))
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
            onClick = onSearchClicked,
            Modifier.constrainAs(share) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SearchItem(
    //navController: NavController,
    place: Place?,
    modifier: Modifier = Modifier,
    onItemClick: (Place) -> Unit
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
            .clickable { onItemClick(place!!) },

        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart

        ) {
            NetworkImage(
                url = place!!.image[0],
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.compositedOnSurface(alpha = 0.2f))
                )
            }
        }
        PlaceGradient(modifier = Modifier.fillMaxSize())
        Text(
            text = place?.name ?: "",
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            color = Color.White,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 8.dp)
        )
    }
}

@Composable
fun PlaceGradient(modifier: Modifier) {
    Spacer(
        modifier = modifier.verticalGradient()
    )
}
