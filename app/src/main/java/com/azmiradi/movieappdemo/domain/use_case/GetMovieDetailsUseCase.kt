package com.azmiradi.movieappdemo.domain.use_case

import com.azmiradi.movieappdemo.domain.Resource
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(movieID: Int): Flow<Resource<MovieItem>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getMovieDetails(movieID = movieID)))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }.flowOn(Dispatchers.IO)
}