package com.azmiradi.movieappdemo.domain.use_case

import androidx.paging.PagingData
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<PagingData<MovieItem>> = repository.getAllMovies()
}