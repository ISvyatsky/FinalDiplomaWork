package com.example.gomeltravelapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.gomeltravelapp.presentation.navigation.SetUpNavGraph
import com.example.gomeltravelapp.presentation.ui.theme.GomelTravelAppTheme
import dagger.hilt.android.AndroidEntryPoint

//Класс является начальным классом, точкой входа, откуда начинается всё приложение
//содержит метод, реализующий навигацию по приложению
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelAppMain()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun TravelAppMain() {

        GomelTravelAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                SetUpNavGraph()
            }
        }

    }
}
