package com.azmiradi.movieappdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.azmiradi.movieappdemo.prsentation.screens.navigation.BottomBar
import com.azmiradi.movieappdemo.prsentation.screens.navigation.Destinations
import com.azmiradi.movieappdemo.prsentation.screens.navigation.NavigationController
import com.azmiradi.movieappdemo.prsentation.screens.search.MovieSearch
import com.azmiradi.movieappdemo.ui.theme.MovieAppDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val showSearchBar = rememberSaveable {
                mutableStateOf(true)
            }
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                showSearchBar.value = destination.route != Destinations.MovieDetails.route
            }
            MovieAppDemoTheme (){
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(visible = showSearchBar.value) {
                            BottomBar(navController)
                        }
                    },
                    topBar = {
                        AnimatedVisibility(visible = showSearchBar.value) {
                            MovieSearch(navController)
                        }
                    },
                ) {
                    NavigationController(navController, it)
                }
            }
        }
    }
}