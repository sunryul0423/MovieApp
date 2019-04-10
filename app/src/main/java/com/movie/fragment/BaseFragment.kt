package com.movie.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.movie.model.request.ApiRequest
import com.movie.server.RetrofitManager
import com.movie.server.RxResponseManager

open class BaseFragment : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var rxResponseManager: RxResponseManager
    protected lateinit var apiRequest: ApiRequest

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxResponseManager = RxResponseManager(mContext)
        apiRequest = RetrofitManager.creare(ApiRequest::class.java)
    }


}