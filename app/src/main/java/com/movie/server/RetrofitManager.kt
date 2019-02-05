package com.movie.server

import com.movie.common.constants.MovieConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private var retrofit: Retrofit

    init {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        retrofit = Retrofit.Builder()
                .baseUrl(MovieConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    internal fun <T> creare(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }

}