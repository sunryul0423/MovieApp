package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.dialog.ProgressDialog
import com.movie.model.request.ApiRequest

class DetailSimilarViewModelFactory(
    private val apiRequest: ApiRequest,
    private val progress: ProgressDialog,
    private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailSimilarViewModel(apiRequest, progress, movieId) as T
    }
}