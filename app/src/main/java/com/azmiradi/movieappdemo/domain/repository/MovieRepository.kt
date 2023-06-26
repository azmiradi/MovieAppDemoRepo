package com.azmiradi.movieappdemo.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getNowPlaying(): Flow<PagingData<MovieItem>>
    fun getTopRated(): Flow<PagingData<MovieItem>>
    fun searchMovie(keyword: String): Flow<PagingData<MovieItem>>
    suspend fun insertMovie(movie: MovieItem): Long
    suspend fun deleteMovie(movie: MovieItem): Int

    fun getAllMovies(): Flow<PagingData<MovieItem>>
    suspend fun getMovieDetails(movieID: String): MovieItem
}