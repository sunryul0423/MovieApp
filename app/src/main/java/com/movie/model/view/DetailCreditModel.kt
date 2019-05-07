package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.model.data.CreditResponse

class DetailCreditModel(private val __creditResponse: CreditResponse) : BaseViewModel() {

    private val _creditRespons = MutableLiveData<CreditResponse>().apply { value = __creditResponse }
    val creditResponse: LiveData<CreditResponse> get() = _creditRespons
}