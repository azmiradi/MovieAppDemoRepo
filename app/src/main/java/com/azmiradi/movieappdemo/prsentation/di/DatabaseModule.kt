package com.azmiradi.movieappdemo.prsentation.di

import android.app.Application
import androidx.room.Room
import com.azmiradi.movieappdemo.data.db.Database
import com.azmiradi.movieappdemo.data.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app:Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "movie_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: Database): MoviesDao {
        return database.getMovieDao()
    }

}