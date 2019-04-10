package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDetailResponse(
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection,
    var budget: Int,
    var genres: List<Genres>,

    var homepage: String,
    var id: Int,
    @SerializedName("imdb_id")
    var imdbId: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompanies>,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountries>,
    @SerializedName("release_date")
    var releaseDate: String,
    var revenue: Int,
    @SerializedName("runtime")
    var runTime: Int,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguages>,
    var status: String,
    @SerializedName("tagline")
    var tagLine: String,
    var title: String,
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
) : Serializable {

    inner class BelongsToCollection : Serializable

    inner class Genres : Serializable {
        var id: Int = 0
        var name: String = ""
    }

    inner class ProductionCompanies : Serializable {
        var id: Int = 0
        @SerializedName("logo_path")
        var logoPath: String = ""
        var name: String = ""
        @SerializedName("origin_country")
        var originCountry: String = ""
    }

    inner class ProductionCountries : Serializable {
        @SerializedName("iso_3166_1")
        var iso31661: String = ""
        var name: String = ""
    }

    inner class SpokenLanguages : Serializable {
        @SerializedName("iso_639_1")
        var iso6391: String = ""
        var name: String = ""
    }
}