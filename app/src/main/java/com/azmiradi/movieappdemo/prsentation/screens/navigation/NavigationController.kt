package com.azmiradi.movieappdemo.prsentation.screens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.azmiradi.movieappdemo.prsentation.screens.details_screen.MovieDetailsScreen
import com.azmiradi.movieappdemo.prsentation.screens.movie.MovieScreen
import com.azmiradi.movieappdemo.prsentation.screens.movie.MovieViewModel

@Composable
fun NavigationController(navigationController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navigationController,
        startDestination = Destinations.NowPlayingMovie.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(
            Destinations.NowPlayingMovie.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetails.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(
                movies = viewModel.nowPlayingMovies.collectAsLazyPagingItems(),
                navigationController
            )
        }


        composable(
            Destinations.TopRatedMovie.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetails.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }

        ) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(
                movies = viewModel.topRatedMovies.collectAsLazyPagingItems(),
                navigationController
            )
        }


        composable(
            Destinations.FavoriteMovie.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.MovieDetails.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }

        ) {
            val viewModel = hiltViewModel<MovieViewModel>()
            MovieScreen(
                movies = viewModel.getFavoriteMovies.collectAsLazyPagingItems(),
                navigationController
            )
        }

        composable(Destinations.MovieDetails.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movie_id") ?: "0"
            MovieDetailsScreen(
                movieId = movieId.toInt(),
                navHostController = navigationController
            )
        }
    }
}
