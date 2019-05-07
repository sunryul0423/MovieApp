package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoResponse(
    var id: Int,
    var results: List<Videos>
) : Serializable {

    data class Videos(
        var site: String,
        var id: String,
        var size: Int,
        @SerializedName("iso_639_1")
        var iso6391: String,
        var key: String,
        @SerializedName("iso_3166_1")
        var iso31661: String,
        var name: String,
        var type: String
    )
}