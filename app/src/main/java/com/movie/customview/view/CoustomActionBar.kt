package com.movie.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.movie.R
import com.movie.interfaces.IActionBarClick

class CoustomActionBar : LinearLayout, View.OnClickListener {

    private val mContext: Context

    private lateinit var ivMenuLeft: ImageView
    private lateinit var tvMenuName: TextView
    private lateinit var llMenuRight: LinearLayout
    private lateinit var ivMenuRight: ImageView

    private var mActionBarLeftClick: IActionBarClick? = null
    private var mActionBarRightClick: IActionBarClick? = null

    constructor(context: Context) : super(context) {
        mContext = context
        setView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        setView()
    }

    private fun setView() {
        val view = View.inflate(mContext, R.layout.view_action_bar, null)
        val llMenuLeft = view.findViewById<LinearLayout>(R.id.ll_menu_left)
        llMenuLeft.setOnClickListener(this)
        ivMenuLeft = view.findViewById(R.id.iv_menu_left)
        tvMenuName = view.findViewById(R.id.tv_menu_name)
        llMenuRight = view.findViewById(R.id.ll_menu_right)
        llMenuRight.setOnClickListener(this)
        ivMenuRight = view.findViewById(R.id.iv_menu_right)
        setChangeIcon(false)
        addView(view)
    }

    fun setChangeIcon(isBackIcon: Boolean) {
        ivMenuRight.background = ContextCompat.getDrawable(mContext, R.drawable.baseline_search_black)
        if (isBackIcon) {
            ivMenuLeft.background = ContextCompat.getDrawable(mContext, R.drawable.baseline_arrow_back_ios_black)
        } else {
            ivMenuLeft.background = ContextCompat.getDrawable(mContext, R.drawable.baseline_menu_black)
        }
    }

    fun setTitle(title: String) {
        tvMenuName.text = title
    }

    fun setActionBarLeftClick(actionBarLeftClick: IActionBarClick) {
        mActionBarLeftClick = actionBarLeftClick
    }

    fun setActionBarRightClick(actionBarRightClick: IActionBarClick) {
        mActionBarRightClick = actionBarRightClick
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_menu_left -> {
                mActionBarLeftClick?.onLeftClick()
            }
            R.id.ll_menu_right -> {
                mActionBarRightClick?.onRightClick()
            }
        }
    }

}