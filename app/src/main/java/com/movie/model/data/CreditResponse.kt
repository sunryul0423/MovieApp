package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreditResponse(
    var id: Int,
    var cast: List<Cast>,
    var crew: List<Crew>
) : Serializable {

    data class Cast(
        @SerializedName("cast_id")
        var castId: Int,
        var character: String,
        @SerializedName("credit_id")
        var creditId: String,
        var gender: Int,
        var id: Int,
        var name: String,
        var order: Int,
        @SerializedName("profile_path")
        var profilePath: String
    ) : Serializable

    data class Crew(
        @SerializedName("credit_id")
        var creditId: String,
        var department: String,
        var gender: Int,
        var id: Int,
        var job: String,
        var name: String,
        @SerializedName("profile_path")
        var profilePath: String
    ) : Serializable
}