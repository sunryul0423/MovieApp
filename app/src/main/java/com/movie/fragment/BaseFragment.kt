package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.movie.R
import com.movie.databinding.ViewMovieListItemBinding
import com.movie.model.request.ApiRequest
import org.koin.android.ext.android.inject

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var viewBinding: T
    protected abstract val layoutResourceId: Int
    protected val apiRequest: ApiRequest by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewBinding.root
    }


}