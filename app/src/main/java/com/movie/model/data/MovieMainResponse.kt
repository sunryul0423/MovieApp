package com.movie.model.data

import com.google.gson.annotations.SerializedName


data class MovieMainResponse(
    var page: Int, var results: MutableList<Movie>,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
) {
    data class Movie(
        @SerializedName("poster_path")
        var posterPath: String,
        var adult: Boolean,
        @SerializedName("overview")
        var overView: String,
        @SerializedName("release_date")
        var releaseDate: String,
        @SerializedName("genre_ids")
        var genreIds: IntArray,
        var id: Int,
        @SerializedName("original_title")
        var originalTitle: String,
        @SerializedName("original_language")
        var originalLanguage: String,
        var title: String,
        @SerializedName("backdrop_path")
        var backdropPath: String,
        var popularity: Double,
        @SerializedName("vote_count")
        var voteCount: Int,
        var video: Boolean,
        @SerializedName("vote_average")
        var voteAverage: Double
    )
}