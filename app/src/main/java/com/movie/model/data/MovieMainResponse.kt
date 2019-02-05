package com.movie.model.data

import com.google.gson.annotations.SerializedName


class MovieMainResponse {

    var page: Int? = null
    var results: MutableList<Movie>? = null

    inner class Movie {
        @SerializedName("poster_path")
        var posterPath: String? = null
        var adult: Boolean = false
        @SerializedName("overview")
        var overView: String = ""
        @SerializedName("release_date")
        var releaseDate: String = ""
        @SerializedName("genre_ids")
        var genreIds: IntArray = intArrayOf()
        var id: Int = 0
        @SerializedName("original_title")
        var originalTitle: String = ""
        @SerializedName("original_language")
        var originalLanguage: String = ""
        var title: String = ""
        @SerializedName("backdrop_path")
        var backdropPath: String? = null
        var popularity: Double = 0.0
        @SerializedName("vote_count")
        var voteCount: Int = 0
        var video: Boolean = false
        @SerializedName("vote_average")
        var voteAverage: Double = 0.0
    }

    @SerializedName("total_results")
    var totalResults: Int? = null
    @SerializedName("total_pages")
    var totalPages: Int = 0

}