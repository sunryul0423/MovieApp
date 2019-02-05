package com.movie.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout

class CustomIndicator : LinearLayout {

    private val mContext: Context

    private var animDuration: Int = 300
    private var itemMargin: Int = 10
    private var mDefaultCircle: Int = 0
    private var mSelectCircle: Int = 0
    private lateinit var imageDot: Array<ImageView?>

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    fun setAnimDuration(animDuration: Int) {
        this.animDuration = animDuration
    }

    fun setItemMargin(itemMargin: Int) {
        this.itemMargin = itemMargin
    }

    fun createDotPanel(count: Int, defaultCircle: Int, selectCircle: Int) {

        mDefaultCircle = defaultCircle
        mSelectCircle = selectCircle

        imageDot = arrayOfNulls(count)

        for (i in 0 until count) {
            imageDot[i] = ImageView(mContext)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.topMargin = itemMargin
            params.bottomMargin = itemMargin
            params.leftMargin = itemMargin
            params.rightMargin = itemMargin
            params.gravity = Gravity.CENTER

            imageDot[i]?.layoutParams = params
            imageDot[i]?.setImageResource(defaultCircle)
            imageDot[i]?.id?.let { imageDot[i]?.setTag(it, false) }
            addView(imageDot[i])
        }

        //첫인덱스 선택
        selectDot(0)
    }

    fun selectDot(position: Int) {

        for (i in imageDot.indices) {
            if (i == position) {
                imageDot[i]?.setImageResource(mSelectCircle)
                imageDot[i]?.let { selectScaleAnim(it, 1f, 1f) }
            } else {
                if (imageDot[i]?.id?.let { imageDot[i]?.getTag(it) } as Boolean) {
                    imageDot[i]?.setImageResource(mDefaultCircle)
                    imageDot[i]?.let { defaultScaleAnim(it, 1f, 1f) }
                }
            }
        }
    }

    private fun selectScaleAnim(view: View, startScale: Float, endScale: Float) {
        val anim = ScaleAnimation(startScale,
                endScale,
                startScale,
                endScale,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)
        anim.fillAfter = true
        anim.duration = animDuration.toLong()
        view.startAnimation(anim)
        view.setTag(view.id, true)
    }

    private fun defaultScaleAnim(view: View, startScale: Float, endScale: Float) {
        val anim = ScaleAnimation(startScale,
                endScale,
                startScale,
                endScale,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)
        anim.fillAfter = true
        anim.duration = animDuration.toLong()
        view.startAnimation(anim)
        view.setTag(view.id, false)
    }
}