package com.azmiradi.movieappdemo.prsentation.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.azmiradi.movieappdemo.prsentation.screens.movie.MovieScreen
import com.azmiradi.movieappdemo.prsentation.screens.movie.MovieViewModel

@Composable
fun NavigationController(navigationController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navigationController,
        startDestination = Destinations.NowPlayingMovie.root,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Destinations.NowPlayingMovie.root) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(movies = viewModel.nowPlayingMovies.collectAsLazyPagingItems())
        }

        composable(Destinations.TopRatedMovie.root) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(movies = viewModel.topRatedMovies.collectAsLazyPagingItems())

        }

        composable(Destinations.FavoriteMovie.root) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(movies = viewModel.getFavoriteMovies.collectAsLazyPagingItems())
        }

        composable(Destinations.MovieDetails.root) {

        }
    }
}
