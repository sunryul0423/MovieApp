package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreditResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
) : Serializable {

    data class Cast(
        @SerializedName("cast_id")
        val castId: Int,
        val character: String,
        @SerializedName("credit_id")
        val creditId: String,
        val gender: Int,
        val id: Int,
        val name: String,
        val order: Int,
        @SerializedName("profile_path")
        val profilePath: String
    ) : Serializable

    data class Crew(
        @SerializedName("credit_id")
        val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        val name: String,
        @SerializedName("profile_path")
        val profilePath: String
    ) : Serializable
}