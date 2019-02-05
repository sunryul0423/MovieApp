package com.movie.server

import android.content.Context
import android.os.Handler
import android.util.Log
import com.movie.`interface`.IRxResult
import com.movie.dialog.ProgressDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RxResponseManager(mContext: Context) {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var count: Int = 0
    var progress: ProgressDialog = ProgressDialog(mContext)
    val handler: Handler = Handler()

    inline fun <reified T> add(disposable: Observable<T>, result: IRxResult) {
        ++count
        if (!progress.isShowing) {
            handler.post {
                progress.show()
            }
        }
        compositeDisposable.add(disposable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: T ->
                    --count
                    Log.d("srpark", "성공")
                    result.onNext(response)
                    if (count == 0 && progress.isShowing) {
                        handler.post {
                            progress.dismiss()
                        }
                    }
                }, { error: Throwable ->
                    --count
                    Log.d("srpark", T::class.java.simpleName + "실패 : " + error.toString())
                    result.onErrer(error)
                    if (count == 0 && progress.isShowing) {
                        handler.post {
                            progress.dismiss()
                        }
                    }
                })
        )
    }

    fun dispose() {
//        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}