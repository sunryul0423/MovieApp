package com.movie.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView
import com.movie.interfaces.OnScrollListener

class CustomScrollView : NestedScrollView {

    private val mContext: Context

    private var mOnScrollListener: OnScrollListener? = null

    private var mIsEnableScroll = true

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        mContext = context
    }

    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
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