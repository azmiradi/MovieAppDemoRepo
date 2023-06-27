package com.azmiradi.movieappdemo.prsentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.azmiradi.movieappdemo.domain.use_case.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {
    fun searchMovies(keyword: String) = searchMoviesUseCase(keyword)
        .cachedIn(viewModelScope)
}