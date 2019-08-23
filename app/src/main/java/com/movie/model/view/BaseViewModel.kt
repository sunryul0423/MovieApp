package com.movie.model.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val throwableData = MutableLiveData<Throwable>()
    protected val progress = MutableLiveData<Boolean>()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getThrowableData(): LiveData<Throwable> {
        return throwableData
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun onError(throwable: Throwable) {
        if (progress.value!!) {
            progress.value = false
        }
        throwableData.value = throwable
//        Crashlytics.logException(throwable)
    }
}