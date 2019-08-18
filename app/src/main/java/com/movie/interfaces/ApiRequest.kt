package com.movie.interfaces

import com.movie.common.*
import com.movie.model.data.CreditResponse
import com.movie.model.data.MovieDetailResponse
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.VideoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApiRequest {
    @GET(URL_UPCOMING)
    fun getUpcoming(@QueryMap option: Map<String, String> = getParam()): Single<MovieMainResponse>

    @GET(URL_NOWPLAYING)
    fun getNowPlaying(@QueryMap option: Map<String, String> = getParam()): Single<MovieMainResponse>

    @GET(URL_POPULAR)
    fun getPopular(@QueryMap option: Map<String, String> = getParam()): Single<MovieMainResponse>

    @GET(URL_TOPRATED)
    fun getTopRated(@QueryMap option: Map<String, String> = getParam()): Single<MovieMainResponse>

    @GET(URL_DETAILS) // 영화 상세
    fun getDetail(@Path(MOVIE_ID) id: Int, @QueryMap option: Map<String, String> = getParam()): Single<MovieDetailResponse>

    @GET(URL_CREDITS) //출연진
    fun getCredit(@Path(MOVIE_ID) id: Int, @QueryMap option: Map<String, String> = getParam()): Single<CreditResponse>

    @GET(URL_SIMILAR) //관련영화
    fun getSimilar(@Path(MOVIE_ID) id: Int, @QueryMap option: Map<String, String> = getParam()): Single<MovieMainResponse>

    @GET(URL_VIDEOS) //비디오
    fun getVideos(@Path(MOVIE_ID) id: Int, @QueryMap option: Map<String, String> = hashMapOf(API_KEY to TMDB_API_KEY)): Single<VideoResponse>

    @GET(URL_SEARCH) //검색
    fun getSearch(@QueryMap option: Map<String, String>, @Query(PAGE) page: Int): Single<MovieMainResponse>
}
