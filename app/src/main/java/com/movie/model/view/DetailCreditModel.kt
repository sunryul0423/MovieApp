package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.model.data.CreditResponse

class DetailCreditModel : BaseViewModel() {

    private val creditResponse = MutableLiveData<CreditResponse>()

    fun setCreditResponse(creditResponse: CreditResponse) {
        this.creditResponse.value = creditResponse
    }

    fun getCreditResponse(): LiveData<CreditResponse> {
        return creditResponse
    }
}