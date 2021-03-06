package com.movie.di

import com.movie.interfaces.ApiRequest
import com.movie.model.view.*
import com.movie.util.BASE_URL
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * API 모듈은 싱글톤으로 생성
 */
val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
            .create(ApiRequest::class.java)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .cookieJar(CookieJar.NO_COOKIES)
            .addInterceptor(get() as HttpLoggingInterceptor)
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

val viewModelFactoryModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailOverviewViewModel(get()) }
    viewModel { DetailSimilarViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailCreditModel() }
    viewModel { DetailCreditCastViewModel() }
}

val appModule = listOf(apiModule, viewModelFactoryModule)