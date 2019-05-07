package com.movie.model.view

import androidx.lifecycle.ViewModel
import com.movie.dialog.ProgressDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected var count = 0

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        ++count
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    open fun progressCancel() {
        --count
    }
}