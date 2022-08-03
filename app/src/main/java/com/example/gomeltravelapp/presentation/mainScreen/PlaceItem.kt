package com.example.gomeltravelapp.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.components.NetworkImage
import com.example.gomeltravelapp.presentation.navigation.Screen
import com.example.gomeltravelapp.presentation.ui.theme.compositedOnSurface
import com.example.gomeltravelapp.presentation.widget.BookMarkButton

@Composable
fun PlaceItem(
    navController: NavController,
    parkPlace: Place,
    modifier: Modifier = Modifier,
    //onDetails: (Int) -> Unit,
    onBookMark: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .width(235.dp)
            .padding(5.dp, 0.dp, 8.dp, 0.dp)
    ) {
        val (image, time, title, source) = createRefs()
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp)
                .clickable {
                    navController.navigate(Screen.PlaceDetailScreen.route + "?id=${parkPlace.id}")
                }
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
