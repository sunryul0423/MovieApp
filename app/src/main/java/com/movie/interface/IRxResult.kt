package com.movie.`interface`

interface IRxResult {

    fun <T> onNext(response: T)

    fun onErrer(error: Throwable)
}