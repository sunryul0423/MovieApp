package com.movie.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoResponse(
    val id: Int,
    val results: List<Videos>
) : Serializable {

    data class Videos(
        val site: String,
        val id: String,
        val size: Int,
        @SerializedName("iso_639_1")
        val iso6391: String,
        val key: String,
        @SerializedName("iso_3166_1")
        val iso31661: String,
        val name: String,
        val type: String
    )
}