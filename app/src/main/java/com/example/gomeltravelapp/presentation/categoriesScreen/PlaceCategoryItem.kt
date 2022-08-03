package com.example.gomeltravelapp.presentation.categoriesScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.gomeltravelapp.domain.model.TypePlace
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.ui.theme.black
import com.example.gomeltravelapp.presentation.ui.theme.blue
import com.example.gomeltravelapp.presentation.ui.theme.blueLight

@Composable
fun Loader(image: Int) {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(LottieCompositionSpec.RawRes(image) )

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
            .fillMaxSize()
            .padding(bottom = 25.dp)
    )
}

@Composable
fun CategoryItem(
    navController: NavController,
    placeCategory: TypePlace,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("placeCategory", placeCategory)
                    navController.navigate(Screen.PlaceListScreen.route)
                },

            shape = RoundedCornerShape(16.dp),
            backgroundColor = blueLight,
            border = (BorderStroke(2.dp, SolidColor(blue)))
        ) {
            /*NetworkImage(
                url = /*placeCategory.icon ?: */"",
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.compositedOnSurface(alpha = 0.2f))
                )
            }*/
            Loader(placeCategory.image)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                Arrangement.Bottom
            ) {
                Text(
                    text = placeCategory.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = black,
                )
                Text(
                    text = "${placeCategory.countPlaces} объектов",
                    fontSize = 11.sp,
                    color = black,
                )
            }
        }
    }
}