package com.example.gomeltravelapp.presentation.welcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.gomeltravelapp.presentation.navigation.MainActions
import kotlinx.coroutines.delay
import com.example.gomeltravelapp.R

//Класс,в котором реализовывается интерфейс экрана загрузки
@Composable
fun SplashScreenView(actions: MainActions) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.ic_cathedral),
                contentDescription = "Logo"
            )
        }
    }

    produceState(initialValue = -1) {
        delay(3000)
        actions.gotoOnBoarding()
    }
}

@Preview(showBackground = true)
@Composable
fun Show() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    SplashScreenView(actions)
}