package com.azmiradi.movieappdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azmiradi.movieappdemo.domain.entity.MovieItem

@Database(entities = [MovieItem::class],version = 1,exportSchema = false)
abstract  class Database : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
}