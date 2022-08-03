package com.example.gomeltravelapp.presentation.welcomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.gomeltravelapp.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.example.gomeltravelapp.presentation.navigation.MainActions
import com.example.gomeltravelapp.presentation.ui.theme.blue

//Класс,в котором реализовывается интерфейс экрана с приветсвием
@Composable
fun Loader(pageCount: Int) {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(LottieCompositionSpec.RawRes(pageCount) )

    val progress by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )

    LottieAnimation(
    compositionResult.value,
    progress,
    modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)
    )
}

@Composable
fun OnBoardingScreenView(actions: MainActions) {

    var pageCount by remember { mutableStateOf(0) }

    val onBoardingHeadingList =
        listOf(
            stringResource(id = R.string.onboarding_heading_1),
            stringResource(id = R.string.onboarding_heading_2),
            stringResource(id = R.string.onboarding_heading_3)
        )

    val onBoardingImagesList =
        listOf(
            R.raw.cath,
            R.raw.bookmarks,
            R.raw.map
        )

    val onBoardingDescriptionList =
        listOf(
            stringResource(id = R.string.onboarding_description_1),
            stringResource(id = R.string.onboarding_description_2),
            stringResource(id = R.string.onboarding_description_3)
        )

    if (pageCount == 3) {
        produceState(initialValue = -1) { gotoDashboard(actions) }
        return
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)

        ) {
            Loader(onBoardingImagesList[pageCount])
            Spacer(modifier = Modifier.height(44.dp))
            Text(
                text = onBoardingHeadingList[pageCount],
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = onBoardingDescriptionList[pageCount],
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (pageCount == 0) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (pageCount == 0) blue else Color.LightGray),
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(
                    modifier = Modifier
                        .size(if (pageCount == 1) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (pageCount == 1) blue else Color.LightGray),
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(
                    modifier = Modifier
                        .size(if (pageCount == 2) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (pageCount == 2) blue else Color.LightGray),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    modifier = Modifier.clickable { gotoDashboard(actions) },
                    text = "Пропустить",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Button(
                    onClick = {
                        pageCount++
                    },
                    elevation = null,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = blue
                    ),
                ) {
                    Text(
                        text = "Далее",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun  Prew() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    OnBoardingScreenView(actions)
}

private fun gotoDashboard(actions: MainActions) {
    actions.gotoOnDashboard()
}