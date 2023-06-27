package com.azmiradi.movieappdemo.prsentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.azmiradi.movieappdemo.domain.use_case.GetFavoriteMoviesUseCase
import com.azmiradi.movieappdemo.domain.use_case.GetNowPlayingMoviesUseCase
import com.azmiradi.movieappdemo.domain.use_case.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    topRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    nowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

) : ViewModel() {
    val topRatedMovies = topRatedMoviesUseCase()
        .cachedIn(viewModelScope)

    val nowPlayingMovies = nowPlayingMoviesUseCase()
        .cachedIn(viewModelScope)

    val getFavoriteMovies = getFavoriteMoviesUseCase()
        .cachedIn(viewModelScope)
}