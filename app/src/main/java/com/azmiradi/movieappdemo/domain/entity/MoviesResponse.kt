package com.azmiradi.movieappdemo.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int? = null,
    @field:SerializedName("total_pages")
    val totalPages: Int? = null,
    val results: List<MovieItem?>? = null,
    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

@Entity
open class MovieItem(
    val title: String? = null,
    @field:SerializedName("poster_path")
    val posterPath: String? = null,
    @field:SerializedName("vote_average")
    val voteAverage: Float? = null,
    @PrimaryKey
    val id: Int? = null,
    @field:SerializedName("vote_count")
    val voteCount: Int? = null,
    val overview: String? = null,
)

data class Genre(val id: Int? = null, val name: String)


