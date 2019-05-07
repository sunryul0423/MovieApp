package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.customview.adapter.CreditListAdapter
import com.movie.model.data.CreditResponse

class DetailCreditCastViewModel : BaseViewModel() {

    private val _castList = MutableLiveData<List<CreditResponse.Cast>>()
    val castList: LiveData<List<CreditResponse.Cast>> get() = _castList

    private val _crewList = MutableLiveData<List<CreditResponse.Crew>>()
    val crewList: LiveData<List<CreditResponse.Crew>> get() = _crewList

    val creditCastAdapter = CreditListAdapter()
    val creditCrewAdapter = CreditListAdapter()


    fun setCastList(castList: List<CreditResponse.Cast>) {
        _castList.value = castList
    }

    fun setCrewList(crewList: List<CreditResponse.Crew>) {
        _crewList.value = crewList
    }

}