package com.example.gomeltravelapp.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gomeltravelapp.domain.model.TypePlace
import com.example.gomeltravelapp.presentation.mapScreen.MapViewModel
import com.example.gomeltravelapp.presentation.categoriesScreen.Categories
import com.example.gomeltravelapp.presentation.bookmarkScreen.BookMark
import com.example.gomeltravelapp.presentation.bookmarkScreen.BookmarkViewModel
import com.example.gomeltravelapp.presentation.mapScreen.MapScreen
import com.example.gomeltravelapp.presentation.navigationScreen.MainContentScreen
import com.example.gomeltravelapp.presentation.placeDetailScreen.PlaceDetailScreen
import com.example.gomeltravelapp.presentation.placeListScreen.PlaceListScreen
import com.example.gomeltravelapp.presentation.welcomeScreen.OnBoardingScreenView
import com.example.gomeltravelapp.presentation.welcomeScreen.SplashScreenView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

//Класс, который реализует навигацию - переходы по экранам
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
    InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class
)
@ExperimentalFoundationApi
@Composable
fun SetUpNavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    val bookmarkViewModel: BookmarkViewModel = viewModel()
    val mapViewModel: MapViewModel = viewModel()

    NavHost(navController, startDestination = Screen.Splash.route) {

        //Splash Screen
        composable(Screen.Splash.route) {
            SplashScreenView(actions)
        }

        //OnBoarding Screen
        composable(Screen.OnBoarding.route) {
            OnBoardingScreenView(actions)
        }

        //Dashboard
        composable(Screen.Dashboard.route) {
            MainContentScreen(actions, navController, bookmarkViewModel, mapViewModel)
        }

        //P;ace Category Screen
        composable(Screen.PlaceCategoryScreen.route) {
            Categories(navController)
        }

        composable(Screen.BookmarkScreen.route) {
            BookMark(navController, bookmarkViewModel)
        }

        composable(Screen.MapScreen.route) {
            MapScreen(mapViewModel, navController)
        }

        composable(Screen.PlaceListScreen.route) {
            val placeCategory = navController.previousBackStackEntry?.savedStateHandle?.get<TypePlace>("placeCategory")
            placeCategory?.let { PlaceListScreen(actions, navController, placeCategory) }
        }

        composable(
            route = Screen.PlaceDetailScreen.route + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            PlaceDetailScreen(navController)
        }
    }
}

class MainActions(private val navController: NavHostController) {

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoOnBoarding: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.OnBoarding.route)
    }

    val gotoOnDashboard: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.Dashboard.route)
    }

    val gotoPlaceCategories: () -> Unit = {
        navController.navigate(Screen.PlaceCategoryScreen.route)
    }

    val gotoPlaceList: () -> Unit = {
        navController.navigate(Screen.PlaceListScreen.route)
    }
}