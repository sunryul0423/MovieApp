package com.movie.model.view

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.R
import com.movie.fragment.MovieDetailCreditCastFragment
import com.movie.model.data.CreditResponse
import com.movie.model.request.ApiRequest

class DetailCreditModel : BaseViewModel() {

    private val _creditRespons = MutableLiveData<CreditResponse>()

    val creditResponse: LiveData<CreditResponse> = _creditRespons

    val nameLiveData = MutableLiveData<String>()

    fun changeName(name: String) {
        nameLiveData.postValue(name)
    }

}