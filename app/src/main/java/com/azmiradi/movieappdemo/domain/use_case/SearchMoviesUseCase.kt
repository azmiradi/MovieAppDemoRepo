package com.azmiradi.movieappdemo.domain.use_case

import androidx.paging.PagingData
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(keyword:String): Flow<PagingData<MovieItem>> = repository.searchMovie(keyword)
}