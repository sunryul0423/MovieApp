package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.util.IMAGE_URL

class CreditInfoViewModel : BaseViewModel() {

    private val _url = MutableLiveData<String>()
    private val _name = MutableLiveData<String>()
    private val _part = MutableLiveData<String>()

    val url: LiveData<String> get() = _url
    val name: LiveData<String> get() = _name
    val part: LiveData<String> get() = _part

    fun setImageUrl(url: String?) {
        _url.value = IMAGE_URL + url
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setPart(part: String) {
        _part.value = part
    }
}
