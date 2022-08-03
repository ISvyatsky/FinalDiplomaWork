package com.example.gomeltravelapp.presentation.mapScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.ui.theme.blue

@Composable
fun PlaceItemCard(
    modifier: Modifier = Modifier,
    place: Place,
    onClickItem: (String) -> Unit
) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = place.image[0]
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = androidx.compose.ui.Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
            )
            Text(
                "${place.name}\n",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CardSizeDefault(drawable = R.drawable.ic_star, background = blue)
                    Text(
                        "${place.rate}",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                CardSizeDefault(
                    modifier = Modifier.clickable {
                        onClickItem.invoke(place.id.toString())
                    },
                    background = blue,
                    drawable = R.drawable.ic_back
                )
            }
        }
    }
}

@Composable
fun CardSizeDefault(modifier: Modifier = Modifier, drawable: Int, background: Color = White) {
    Card(
        backgroundColor = background,
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(id = drawable),
            contentDescription = null,
        )
    }
}