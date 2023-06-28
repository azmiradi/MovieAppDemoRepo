package com.azmiradi.movieappdemo.prsentation

import androidx.annotation.StringRes


data class DataState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    @StringRes val error: Int? = null,
)
