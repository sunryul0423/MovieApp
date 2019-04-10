package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.model.data.CreditResponse

class DetailCreditModel : BaseViewModel() {

    private val _creditRespons = MutableLiveData<CreditResponse>()

    val creditResponse: LiveData<CreditResponse> = _creditRespons

    val nameLiveData = MutableLiveData<String>()

    fun changeName(name: String) {
        nameLiveData.postValue(name)
    }

}