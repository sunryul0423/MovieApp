package com.movie.model.data

import com.google.gson.annotations.SerializedName


data class MovieMainResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
) {
    data class Movie(
        @SerializedName("poster_path")
        val posterPath: String,
        val adult: Boolean,
        @SerializedName("overview")
        val overView: String,
        @SerializedName("release_date")
        val releaseDate: String,
        val id: Int,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        val title: String,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        val popularity: Double,
        @SerializedName("vote_count")
        val voteCount: Int,
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double
    )
}