package com.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.movie.dialog.ProgressDialog

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var viewBinding: T
    protected abstract val layoutResourceId: Int
    protected lateinit var progress: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = ProgressDialog(mContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewBinding.root
    }


}