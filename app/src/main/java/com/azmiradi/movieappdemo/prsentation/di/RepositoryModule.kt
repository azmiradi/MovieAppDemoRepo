package com.azmiradi.movieappdemo.prsentation.di

import com.azmiradi.movieappdemo.data.db.MoviesDao
import com.azmiradi.movieappdemo.data.remote.APIService
import com.azmiradi.movieappdemo.data.repository.MovieRepositoryImpl
import com.azmiradi.movieappdemo.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: APIService,
        moviesDao: MoviesDao
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, moviesDao)
    }

}