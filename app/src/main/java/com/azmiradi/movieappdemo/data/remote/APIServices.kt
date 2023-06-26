package com.azmiradi.movieappdemo.data.remote

import com.azmiradi.movieappdemo.BuildConfig
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServices {
    @GET(NOW_PLAYING_END_POINT)
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): MoviesResponse

    @GET(TOP_RATED_END_POINT)
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): MoviesResponse

    @GET(SEARCH_END_POINT)
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("query") keyword: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET(MOVIE_DETAILS_END_POINT)
    suspend fun getMovieDetails(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Path("movie_id") movieID: String
    ): MovieItem
}