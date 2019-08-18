package com.movie.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.movie.R

class CustomDrawerView : LinearLayout {

    private val mContext: Context

    constructor(context: Context) : super(context) {
        mContext = context
        setView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        setView()
    }

    private fun setView() {
        val view = View.inflate(mContext, R.layout.view_drawer_layout, null)
        addView(view)
    }
}