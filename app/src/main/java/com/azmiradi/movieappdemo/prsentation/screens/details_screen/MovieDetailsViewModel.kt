package com.azmiradi.movieappdemo.prsentation.screens.details_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azmiradi.movieappdemo.domain.Resource
import com.azmiradi.movieappdemo.domain.entity.MovieItem
import com.azmiradi.movieappdemo.domain.use_case.DeleteSavedMoveUseCase
import com.azmiradi.movieappdemo.domain.use_case.GetMovieDetailsUseCase
import com.azmiradi.movieappdemo.domain.use_case.SaveMoveUseCase
import com.azmiradi.movieappdemo.prsentation.DataState
import com.azmiradi.movieappdemo.prsentation.exeptions.mapException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveMoveUseCase: SaveMoveUseCase,
    private val deleteSavedMoveUseCase: DeleteSavedMoveUseCase
) : ViewModel() {

    private var job: Job? = null
    private val _movieDetailsState = mutableStateOf(DataState<MovieItem>())
    val movieDetailsState: State<DataState<MovieItem>> = _movieDetailsState

    fun getMovies(movieID: Int) {
        job?.cancel()
        job = movieDetailsUseCase(movieID = movieID).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieDetailsState.value = DataState(isLoading = true)
                }

                is Resource.Error -> {
                    _movieDetailsState.value =
                        DataState(error = it.error?.mapException())
                }

                is Resource.Success -> {
                    _movieDetailsState.value = DataState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveMovie(movie: MovieItem) {
        viewModelScope.launch {
            saveMoveUseCase(movie)
            _movieDetailsState.value = DataState(data = movie.copy(isFavorite = true))
        }
    }

    fun deleteMovie(movie: MovieItem) {
        viewModelScope.launch {
            deleteSavedMoveUseCase(movie)
            _movieDetailsState.value = DataState(data = movie.copy(isFavorite = false))
        }
    }
}

