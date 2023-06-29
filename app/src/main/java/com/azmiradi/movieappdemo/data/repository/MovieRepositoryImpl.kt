package com.azmiradi.movieappdemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.azmiradi.movieappdemo.data.db.MoviesDao
import com.azmiradi.movieappdemo.data.paging.NowPlayingMoviePagingSource
import com.azmiradi.movieappdemo.data.paging.SearchMoviePagingSource
import com.azmiradi.movieappdemo.data.paging.TopRatedMoviePagingSource
import com.azmiradi.movieappdemo.data.remote.APIService
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

internal class MovieRepositoryImpl constructor(
    private val apiService: APIService,
    private val moviesDao: MoviesDao
) : MovieRepository {

    override  fun getNowPlaying(): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NowPlayingMoviePagingSource(apiService, moviesDao)
            }
        ).flow
    }

    override fun getTopRated(): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                TopRatedMoviePagingSource(apiService, moviesDao)
            }
        ).flow
    }

    override  fun searchMovie(keyword: String): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchMoviePagingSource(apiService, moviesDao, keyword)
            }
        ).flow
    }


    override suspend fun insertMovie(movie: MovieItem): Long {
        return moviesDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieItem): Int {
        return moviesDao.deleteMovies(movie)
    }

    override fun getAllMovies(): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                moviesDao.getAllMovies()
            }
        ).flow
    }

    override suspend fun getMovieDetails(movieID: Int): MovieItem {
        return apiService.getMovieDetails(movieID = movieID)
            .copy(isFavorite = moviesDao.isFavoriteMovie(movieID))
    }



}