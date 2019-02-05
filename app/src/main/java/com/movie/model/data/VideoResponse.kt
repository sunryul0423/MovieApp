package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class VideoResponse : Serializable {

    var id = 0
    var results: List<Videos>? = null

    inner class Videos {
        var site = ""
        var id = ""
        var size = 0
        @SerializedName("iso_639_1")
        var iso6391 = ""
        var key = ""
        @SerializedName("iso_3166_1")
        var iso31661 = ""
        var name = ""
        var type = ""
    }
}