package com.movie.model.data

import com.google.gson.annotations.SerializedName


data class MovieMainResponse(
    val page: Int, val results: List<Movie>,
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
        @SerializedName("genre_ids")
        val genreIds: IntArray,
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
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Movie

            if (posterPath != other.posterPath) return false
            if (adult != other.adult) return false
            if (overView != other.overView) return false
            if (releaseDate != other.releaseDate) return false
            if (!genreIds.contentEquals(other.genreIds)) return false
            if (id != other.id) return false
            if (originalTitle != other.originalTitle) return false
            if (originalLanguage != other.originalLanguage) return false
            if (title != other.title) return false
            if (backdropPath != other.backdropPath) return false
            if (popularity != other.popularity) return false
            if (voteCount != other.voteCount) return false
            if (video != other.video) return false
            if (voteAverage != other.voteAverage) return false

            return true
        }

        override fun hashCode(): Int {
            var result = posterPath.hashCode()
            result = 31 * result + adult.hashCode()
            result = 31 * result + overView.hashCode()
            result = 31 * result + releaseDate.hashCode()
            result = 31 * result + genreIds.contentHashCode()
            result = 31 * result + id
            result = 31 * result + originalTitle.hashCode()
            result = 31 * result + originalLanguage.hashCode()
            result = 31 * result + title.hashCode()
            result = 31 * result + backdropPath.hashCode()
            result = 31 * result + popularity.hashCode()
            result = 31 * result + voteCount
            result = 31 * result + video.hashCode()
            result = 31 * result + voteAverage.hashCode()
            return result
        }
    }
}