package com.azmiradi.movieappdemo.movieappdemo.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azmiradi.movieappdemo.data.db.Database
import com.azmiradi.movieappdemo.data.db.MoviesDao
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var moviesDao: MoviesDao
    private lateinit var appDatabase: Database

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).build()
        moviesDao = appDatabase.getMovieDao()
    }


    @Test
    fun saveMovieTest(): Unit = runBlocking {
        val movie = MovieItem(
            title = "Romantic Store",
            posterPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
            voteAverage = 2.3f,
            id = 1,
           voteCount = 200,
           overview = "Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
        )

        moviesDao.insertMovie(movie)
        val movies = moviesDao.getAllMovies().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        val actualData = movies as PagingSource.LoadResult.Page<Int, MovieItem>

        Truth.assertThat(actualData.data[0].id).isEqualTo(movie.id)
    }

    @Test
    fun deleteMovieTest(): Unit = runBlocking {
        // in source object must name some id to test because converter return name in id and name
        val movie = MovieItem(
            "Romantic Store", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", 2.3f,
            1, 200,
            "Interstellar chronicles the adventures of a group the vast distances involved in an interstellar voyage.",
        )

        moviesDao.insertMovie(movie)
        moviesDao.deleteMovies(movie)

        val movies = moviesDao.getAllMovies().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        val actualData = movies as PagingSource.LoadResult.Page<Int, MovieItem>

        Truth.assertThat(actualData.data.isEmpty())
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}
