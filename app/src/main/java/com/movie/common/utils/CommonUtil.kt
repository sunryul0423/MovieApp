package com.movie.common.utils

import com.movie.common.constants.*

object CommonUtil {

    fun getParam(): Map<String, String> {
        return hashMapOf(API_KEY to TMDB_API_KEY, LANGUAGE to LANGUAGE_KO)
    }

    fun getSearchParam(text: String): Map<String, String> {
        return hashMapOf(API_KEY to TMDB_API_KEY, LANGUAGE to LANGUAGE_KO, REGION to LANGUAGE_KO, QUERY to text)
    }
}