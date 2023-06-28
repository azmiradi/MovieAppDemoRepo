package com.azmiradi.movieappdemo.prsentation.exeptions

import android.util.Log
import com.azmiradi.movieappdemo.R
import retrofit2.HttpException
import java.io.IOException

fun Throwable.mapException(): Int {
    return when (this) {
        is HttpException -> {
            R.string.network_error
        }

        is IOException -> {
            R.string.check_internet_connection
        }

        else -> {
            Log.d("Exception : ", this.message ?: "")
            R.string.something_error
        }
    }

}