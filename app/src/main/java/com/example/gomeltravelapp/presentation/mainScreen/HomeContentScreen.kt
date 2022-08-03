package com.example.gomeltravelapp.presentation.mainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.navigation.MainActions
import com.example.gomeltravelapp.presentation.LoadingContent
import com.example.gomeltravelapp.presentation.PlaceViewModel
import com.example.gomeltravelapp.presentation.SearchWidgetState
import com.example.gomeltravelapp.presentation.ui.theme.*

//Класс,в котором реализовывается интерфейс главного домашнего экрана
@Composable
fun HomeContentScreen(
    navController: NavController,
    actions: MainActions,
    viewModel: PlaceViewModel = hiltViewModel()
) {

    val viewState by viewModel.state.collectAsState()
    val hasError by viewModel.hasError.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val searchText = remember {
        mutableStateOf("")
    }

    val searchTextState by viewModel.searchTextState

    LoadingContent(loading) {
        Surface(
            modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = !hasError) {
                LazyColumn {
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                    item { HeaderTitle() }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item { HeaderSubTitle() }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                    item { SearchAppBar(
                        text = searchTextState,
                        onTextChange = {
                            viewModel.updateSearchTextState(newValue = it)
                            searchText.value = it
                        },
                        onCloseClicked = {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {
                            searchText.value = it
                        }
                    ) }
                    item { Spacer(modifier = Modifier.height(34.dp)) }
                    item { HeaderPopularPlacesTitle(actions) }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                    item {
                        DailyPlaceContent(navController, viewState.allPlaces.sortedByDescending { it.rate }, searchText.value) { place ->
                            viewModel.onBookMark(place)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                    item { HeaderPopularPlacesTitle(actions) }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                    item {
                        DailyPlaceContent(navController, viewState.museumPlaces.sortedByDescending { it.rate }, searchText.value,/*, onDetails*/) { place ->
                            viewModel.onBookMark(place)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}

@Composable
fun HeaderTitle() {
    Text(
        text = stringResource(id = R.string.header_title),
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp, 25.dp, 0.dp)
    )
}

@Composable
fun HeaderSubTitle() {
    Text(
        text = stringResource(id = R.string.header_subtitle),
        color = Color.Gray,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp, 25.dp, 0.dp)
    )
}

@Suppress("LongMethod")
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(

    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp, 25.dp, 0.dp),
            value = text,
            shape = RoundedCornerShape(32.dp),
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = "Найти",
                    color = platinum
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.text_search_icon),
                        tint = lightSilver
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
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
                        tint = lightSilver
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
                backgroundColor = ghostWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun HeaderPopularPlacesTitle(actions: MainActions) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp, 20.dp, 0.dp)
    ) {
        Text(
            text = "Популярное",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = black
        )
        Text(
            modifier = Modifier.clickable { actions.gotoPlaceCategories() },
            text = "Посмотреть всё",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = blue
        )
    }
}

@Composable
fun DailyPlaceContent(
    navController: NavController,
    places: List<Place>,
    searchText: String,
   // onDetails: (Int) -> Unit,
    onBookMark: (Place) -> Unit
) {
    if (!places.isNullOrEmpty()) {
        PlaceContent(
            navController, places, searchText,/*onDetails,*/ onBookMark
        )
    }
}

@Composable
fun PlaceContent(
    navController: NavController,
    places: List<Place>,
    searchText: String,
    //onDetails: (Int) -> Unit,
    onBookMark: (Place) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp)
    ) {
        items(
            places.filter {
                it.name.contains(
                    searchText,
                    ignoreCase = true
                )
            }
        ) { placeItem ->
            PlaceItem(navController, placeItem/*, onDetails = onDetails*/) {
                onBookMark(placeItem)
            }
        }
    }
}