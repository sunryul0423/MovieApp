package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.model.data.MovieDetailResponse
import com.movie.model.request.ApiRequest

class DetailOverviewViewModelFactory(
    private val apiRequest: ApiRequest,
    private val movieDetailResponse: MovieDetailResponse,
    private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailOverviewViewModel(apiRequest, movieDetailResponse, movieId) as T
    }
}