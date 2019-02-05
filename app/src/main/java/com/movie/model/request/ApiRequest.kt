package com.movie.model.request

import com.movie.common.constants.MovieConstant
import com.movie.model.data.CreditResponse
import com.movie.model.data.MovieDetailResponse
import com.movie.model.data.MovieMainResponse
import com.movie.model.data.VideoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApiRequest {
    @GET(MovieConstant.URL_UPCOMING)
    fun getUpcoming(@QueryMap option: Map<String, String>): Observable<MovieMainResponse>

    @GET(MovieConstant.URL_NOWPLAYING)
    fun getNowPlaying(@QueryMap option: Map<String, String>): Observable<MovieMainResponse>

    @GET(MovieConstant.URL_POPULAR)
    fun getPopular(@QueryMap option: Map<String, String>): Observable<MovieMainResponse>

    @GET(MovieConstant.URL_TOPRATED)
    fun getTopRated(@QueryMap option: Map<String, String>): Observable<MovieMainResponse>

    @GET(MovieConstant.URL_DETAILS) // 영화 상세
    fun getDetail(@Path(MovieConstant.MOVIE_ID) id: Int, @QueryMap option: Map<String, String>): Observable<MovieDetailResponse>

    @GET(MovieConstant.URL_CREDITS) //출연진
    fun getCredit(@Path(MovieConstant.MOVIE_ID) id: Int, @QueryMap option: Map<String, String>): Observable<CreditResponse>

    @GET(MovieConstant.URL_SIMILAR) //관련영화
    fun getSimilar(@Path(MovieConstant.MOVIE_ID) id: Int, @QueryMap option: Map<String, String>): Observable<MovieMainResponse>

    @GET(MovieConstant.URL_VIDEOS) //비디오
    fun getVideos(@Path(MovieConstant.MOVIE_ID) id: Int, @QueryMap option: Map<String, String>): Observable<VideoResponse>

    @GET(MovieConstant.URL_SEARCH) //검색
    fun getSearch(@QueryMap option: Map<String, String>, @Query(MovieConstant.PAGE) page: Int): Observable<MovieMainResponse>
}
