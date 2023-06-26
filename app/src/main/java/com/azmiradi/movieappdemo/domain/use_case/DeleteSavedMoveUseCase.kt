package com.azmiradi.movieappdemo.domain.use_case

import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteSavedMoveUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: MovieItem) = repository.deleteMovie(movie)
}