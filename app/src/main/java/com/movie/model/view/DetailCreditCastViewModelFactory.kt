package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailCreditCastViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailCreditCastViewModel() as T
    }
}