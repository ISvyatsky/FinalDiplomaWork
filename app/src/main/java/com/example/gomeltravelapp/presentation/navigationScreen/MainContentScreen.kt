package com.example.gomeltravelapp.presentation.navigationScreen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gomeltravelapp.presentation.navigation.MainActions
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.presentation.mapScreen.MapViewModel
import com.example.gomeltravelapp.presentation.bookmarkScreen.BookMark
import com.example.gomeltravelapp.presentation.bookmarkScreen.BookmarkViewModel
import com.example.gomeltravelapp.presentation.categoriesScreen.Categories
import com.example.gomeltravelapp.presentation.mainScreen.HomeContentScreen
import com.example.gomeltravelapp.presentation.mapScreen.MapScreen
import com.example.gomeltravelapp.presentation.ui.theme.blue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

//Класс,в котором реализовывается интерфейс навигационной панели
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class
)
@ExperimentalMaterialApi
@Composable
fun MainContentScreen(
    actions: MainActions,
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel,
    mapViewModel: MapViewModel
) {
    val sectionState = remember { mutableStateOf(PlaceHomeTabs.Home) }
    val navItems = PlaceHomeTabs.values().toList()

    Scaffold(
        bottomBar = {
            BottomBar(
                items = navItems,
                currentSection = sectionState.value,
                onSectionSelected = { sectionState.value = it },
            )
        }) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(
            modifier = modifier,
            targetState = sectionState.value
        )
        { section ->
            when (section) {
                PlaceHomeTabs.Home -> HomeContentScreen(navController, actions)
                PlaceHomeTabs.Category -> Categories(navController)
                PlaceHomeTabs.BookMark -> BookMark(navController, bookmarkViewModel/*, onDetails = */)
                PlaceHomeTabs.Map -> MapScreen(mapViewModel, navController)
                else -> {}
            }
        }
    }
}


@Composable
private fun BottomBar(
    items: List<PlaceHomeTabs>,
    currentSection: PlaceHomeTabs,
    onSectionSelected: (PlaceHomeTabs) -> Unit
) {
    BottomNavigation(
        modifier = Modifier.height(55.dp),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        items.forEach { section ->

            val selected = section == currentSection

            val iconRes = if (selected) section.selectedIcon else section.icon

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Bottom nav icons"
                    )
                },
                selectedContentColor = blue,
                unselectedContentColor = Color.DarkGray,
                selected = selected,
                onClick = { onSectionSelected(section) },
                alwaysShowLabel = false
            )
        }
    }
}

private enum class PlaceHomeTabs(
    val icon: Int,
    val selectedIcon: Int
) {
    Home(R.drawable.ic_icons8_home__2_, R.drawable.ic_icons8_home__2_),
    Category(R.drawable.ic_city, R.drawable.ic_city),
    BookMark(R.drawable.ic_icons8_bookmark, R.drawable.ic_icons8_bookmark),
    Map(R.drawable.ic_map, R.drawable.ic_mapp),
}
