package com.movie.common

import android.content.Context
import android.widget.Toast
import com.movie.R
import java.io.IOException


fun getParam(): Map<String, String> {
    return hashMapOf(API_KEY to TMDB_API_KEY, LANGUAGE to LANGUAGE_KO)
}

fun getSearchParam(text: String): Map<String, String> {
    return hashMapOf(API_KEY to TMDB_API_KEY, LANGUAGE to LANGUAGE_KO, REGION to LANGUAGE_KO, QUERY to text)
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun showThrowableToast(context: Context, throwable: Throwable) {
    if (throwable is IOException) {
        showToast(context, context.getString(R.string.network_error_msg))
    } else {
        throwable.message?.let { message -> showToast(context, message) }
    }
}