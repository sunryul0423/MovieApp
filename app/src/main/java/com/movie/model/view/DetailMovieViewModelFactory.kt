package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.model.request.ApiRequest

class DetailMovieViewModelFactory(
    private val apiRequest: ApiRequest,
    private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailMovieViewModel(
            apiRequest, movieId
        ) as T
    }
}