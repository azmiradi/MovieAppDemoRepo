package com.azmiradi.movieappdemo.domain

import java.lang.Exception

sealed class Resource<T>(
    val data: T? = null,
    val error: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(exception: Exception) : Resource<T>(error = exception)
}