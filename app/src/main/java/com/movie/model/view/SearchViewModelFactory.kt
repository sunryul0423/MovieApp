package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.dialog.ProgressDialog
import com.movie.model.request.ApiRequest

class SearchViewModelFactory(private val apiRequest: ApiRequest, private val progress: ProgressDialog) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(apiRequest, progress) as T
    }
}