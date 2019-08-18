package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.interfaces.ApiRequest

@Suppress("UNCHECKED_CAST")
class DetailOverviewViewModelFactory(private val apiRequest: ApiRequest) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailOverviewViewModel(apiRequest) as T
    }
}