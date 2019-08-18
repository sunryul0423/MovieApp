package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDetailResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genres>,

    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountries>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    @SerializedName("runtime")
    val runTime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguages>,
    val status: String,
    @SerializedName("tagline")
    val tagLine: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) : Serializable {

    inner class BelongsToCollection : Serializable

    data class Genres(
        val id: Int,
        val name: String
    ) : Serializable

    data class ProductionCompanies(
        val id: Int = 0,
        @SerializedName("logo_path")
        val logoPath: String,
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String
    ) : Serializable

    data class ProductionCountries(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        val name: String
    ) : Serializable

    data class SpokenLanguages(
        @SerializedName("iso_639_1")
        val iso6391: String,
        val name: String
    ) : Serializable
}