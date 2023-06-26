package com.azmiradi.movieappdemo.data.db

import android.content.ClipData
import androidx.paging.PagingSource
import androidx.room.*
import com.azmiradi.movieappdemo.domain.entity.MovieItem

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieItem):Long

    @Query("Select * from MovieItem")
    fun getAllMovies(): PagingSource<Int,MovieItem>

    @Delete
    suspend fun deleteMovies(movie: MovieItem):Int

    @Query("SELECT EXISTS (SELECT 1 FROM MovieItem WHERE id = :movieId LIMIT 1)")
    fun isFavoriteMovie(movieId: Int): Boolean
}