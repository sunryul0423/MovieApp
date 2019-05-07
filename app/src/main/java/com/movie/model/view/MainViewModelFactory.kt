package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.customview.view.CustomIndicator
import com.movie.dialog.ProgressDialog
import com.movie.model.request.ApiRequest

class MainViewModelFactory(
    private val apiRequest: ApiRequest,
    private val progress: ProgressDialog
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(apiRequest, progress) as T
    }
}