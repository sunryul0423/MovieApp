package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CreditResponse : Serializable {

    var id = 0
    var cast: List<Cast> = emptyList()
    var crew: List<Crew> = emptyList()

    inner class Cast : Serializable {

        @SerializedName("cast_id")
        var castId = 0
        var character = ""
        @SerializedName("credit_id")
        var creditId = ""
        var gender = 1
        var id = 0
        var name = ""
        var order = 0
        @SerializedName("profile_path")
        var profilePath: String? = null
    }

    inner class Crew : Serializable {
        @SerializedName("credit_id")
        var creditId = ""
        var department = ""
        var gender = 1
        var id = 0
        var job = ""
        var name = ""
        @SerializedName("profile_path")
        var profilePath: String? = null
    }
}