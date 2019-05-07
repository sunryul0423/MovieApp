package com.movie.model.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie.model.data.CreditResponse
import com.movie.model.request.ApiRequest

class DetailCreditModelFactory(private val creditResponse: CreditResponse) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailCreditModel(creditResponse) as T
    }


}