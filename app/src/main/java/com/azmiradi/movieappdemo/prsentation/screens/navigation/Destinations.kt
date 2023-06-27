package com.azmiradi.movieappdemo.prsentation.screens.navigation

enum class Destinations(val route: String) {
    TopRatedMovie("top_rated_movie"),
    NowPlayingMovie("now_rated_movie"),
    FavoriteMovie("favorite_movie"),
    MovieDetails("movie_details?movie_id={movie_id}")
}