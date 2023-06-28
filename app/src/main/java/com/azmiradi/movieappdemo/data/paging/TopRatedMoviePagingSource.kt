package com.azmiradi.movieappdemo.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azmiradi.movieappdemo.data.db.MoviesDao
import com.azmiradi.movieappdemo.data.remote.APIServices
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopRatedMoviePagingSource(
    private val apiServices: APIServices,
    private val moviesDao: MoviesDao
) : PagingSource<Int, MovieItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            withContext(Dispatchers.IO){
                val page = params.key ?: 1
                val items = apiServices.getTopRated(page = page)

                val mappedMovies = items.results?.map {
                    it.copy(isFavorite = moviesDao.isFavoriteMovie(it.id ?: 0))
                } ?: emptyList()

                LoadResult.Page(
                    data = mappedMovies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (mappedMovies.isEmpty()) null else page + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
