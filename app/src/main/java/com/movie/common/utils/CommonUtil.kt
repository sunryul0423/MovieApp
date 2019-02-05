package com.movie.common.utils

import com.movie.common.constants.MovieConstant


object CommonUtil {

    fun getParam(): Map<String, String> {
        return hashMapOf(MovieConstant.API_KEY to MovieConstant.TMDB_API_KEY, MovieConstant.LANGUAGE to MovieConstant.LANGUAGE_KO)
    }

    fun getSearchParam(text: String): Map<String, String> {
        return hashMapOf(MovieConstant.API_KEY to MovieConstant.TMDB_API_KEY, MovieConstant.LANGUAGE to MovieConstant.LANGUAGE_KO, MovieConstant.REGION to MovieConstant.LANGUAGE_KO, MovieConstant.QUERY to text)
    }
}