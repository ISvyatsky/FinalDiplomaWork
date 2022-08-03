package com.example.gomeltravelapp.presentation.bookmarkScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.components.NetworkImage
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.ui.theme.compositedOnSurface
import com.example.gomeltravelapp.presentation.widget.BookMarkButton

@Composable
fun BookmarkItem(
    navController: NavController,
    parkPlace: Place,
    modifier: Modifier = Modifier,
    onBookMark: () -> Unit
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
                    navController.navigate(Screen.PlaceDetailScreen.route + "?id=${parkPlace.id}")
                },

            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                //.border(BorderStroke(2.dp, SolidColor(blue)))
            ) {
                NetworkImage(
                    url = parkPlace.image[0],
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.compositedOnSurface(alpha = 0.2f))
                    )
                }
                BookMarkButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onBookMark = {
                        onBookMark()
                        parkPlace.saved = !parkPlace.saved
                    },
                    selected = parkPlace.saved
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    //.border(BorderStroke(2.dp, SolidColor(blue))),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${parkPlace.rate}",
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                        //.border(BorderStroke(2.dp, SolidColor(blue)))

                    )

                    Text(
                        text = parkPlace.name ?: "",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp)
                    )
                }
            }
        }
    }
}