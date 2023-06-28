package com.azmiradi.movieappdemo.prsentation.screens.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderTop
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.azmiradi.movieappdemo.R

@Composable
fun BottomBar(navController: NavController, selectedItem: MutableState<Int>) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White
    ) {
        bottomNavigationList.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
                label = {
                    Text(stringResource(id = item.title))
                },
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.root)
                },
                alwaysShowLabel = false,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

data class BottomBarItem(@StringRes val title: Int, val root: String, val icon: ImageVector)

val bottomNavigationList = listOf(
    BottomBarItem(
        R.string.now_playing,
        Destinations.NowPlayingMovie.route,
        Icons.Filled.PlayCircle
    ),
    BottomBarItem(
        R.string.top_rated,
        Destinations.TopRatedMovie.route,
        Icons.Filled.BorderTop
    ),
    BottomBarItem(
        R.string.favorite,
        Destinations.FavoriteMovie.route,
        Icons.Filled.FavoriteBorder
    )
)