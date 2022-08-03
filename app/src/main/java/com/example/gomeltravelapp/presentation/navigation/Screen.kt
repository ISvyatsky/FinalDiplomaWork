package com.example.gomeltravelapp.presentation.navigation

import androidx.annotation.StringRes
import com.example.gomeltravelapp.R

//Класс экранов
sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Splash : Screen("splash", R.string.splash)
    object OnBoarding : Screen("onboarding", R.string.onboarding)
    object Dashboard : Screen("dashboard", R.string.dashboard)
    object PlaceCategoryScreen : Screen("PlaceCategory", R.string.placeCategories)
    object BookmarkScreen : Screen("Bookmark", R.string.bookmarks)
    object MapScreen : Screen("Bookmark", R.string.map)
    object PlaceListScreen : Screen("PlaceList", R.string.placeList)
    object PlaceDetailScreen : Screen("PlaceDetail", R.string.placeDetail)
}