package com.azmiradi.movieappdemo.domain.use_case

import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke() = repository.getTopRated()
}