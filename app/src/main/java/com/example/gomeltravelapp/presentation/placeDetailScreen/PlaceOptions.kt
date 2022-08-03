package com.example.gomeltravelapp.presentation.placeDetailScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gomeltravelapp.R
import com.example.gomeltravelapp.domain.model.Place
import com.example.gomeltravelapp.presentation.widget.BookMarkButton

@Composable
fun PlaceOptions(
    placeDetail: Place,
    onSave: (Place) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        TextButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = placeDetail.rate.toString(),
                //color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp
            )
        }
        BookMarkButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onBookMark = {
                onSave(placeDetail)
                placeDetail.saved = !placeDetail.saved
            },
            selected = placeDetail.saved
        )
        TextButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onClick = { }
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                imageVector = Icons.Filled.Map,
                contentDescription = null,
                //tint = Color.White
            )
            Text(
                text = placeDetail.rate.toString(),
                //color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
