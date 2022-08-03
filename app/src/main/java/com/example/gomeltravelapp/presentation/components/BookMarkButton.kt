package com.example.gomeltravelapp.presentation.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gomeltravelapp.R

@Composable
fun BookMarkButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onBookMark: () -> Unit
) {
    var select by remember { mutableStateOf(selected) }

    val icon = if (select) Icons.Outlined.Bookmark else Icons.Filled.BookmarkBorder
    Surface(
        color = colorResource(id = R.color.black_alpha),
        shape = CircleShape,
        modifier = modifier.requiredSize(36.dp, 36.dp).clickable {
            select = !select
            onBookMark()
        }
    ) {
        Icon(
            imageVector = icon,
            tint = colorResource(id = android.R.color.white),
            contentDescription = null,
            modifier = Modifier
                .padding(6.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBookMarkButtonClicked() {
    BookMarkButton(selected = false) {}
}

@Composable
@Preview(showBackground = true)
fun PreviewBookMarkButtonUnClicked() {
    BookMarkButton(selected = true) {}
}