package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.common.constants.IMAGE_URL

class PagerViewModel : BaseViewModel() {

    private val _imageUrl = MutableLiveData<String>()
    private val _title = MutableLiveData<String>()
    private val _id = MutableLiveData<Int>()

    val imageUrl: LiveData<String> get() = _imageUrl
    val title: LiveData<String> get() = _title
    val id: LiveData<Int> get() = _id

    fun setImageUrl(url: String?) {
        _imageUrl.value = IMAGE_URL + url
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setId(id: Int) {
        _id.value = id
    }
}