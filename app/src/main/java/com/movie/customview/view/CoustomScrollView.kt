package com.movie.customview.view

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.text.BoringLayout
import android.util.AttributeSet
import android.view.MotionEvent
import com.movie.`interface`.OnScrollListener

class CoustomScrollView : NestedScrollView {

    private val mContext: Context

    private var mOnScrollListener: OnScrollListener? = null

    private var mIsEnableScroll = true

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        mContext = context
    }

    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
//        val direction = if (oldY > y) 1 else 0
        mOnScrollListener?.onScroll(y)
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener?) {
        mOnScrollListener = onScrollListener
    }

    fun isEnableScroll(enableScroll: Boolean) {
        mIsEnableScroll = enableScroll
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (mIsEnableScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }
}