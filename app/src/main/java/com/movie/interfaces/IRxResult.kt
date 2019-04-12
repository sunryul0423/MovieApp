package com.movie.interfaces

interface IRxResult {

    fun <T> onNext(response: T)

    fun onErrer(error: Throwable)
}