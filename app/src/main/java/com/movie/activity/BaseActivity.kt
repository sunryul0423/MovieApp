package com.movie.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.movie.R
import com.movie.`interface`.IActionBarClick
import com.movie.customview.view.CoustomActionBar
import com.movie.model.request.ApiRequest
import org.koin.android.ext.android.inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var coustomActionBar: CoustomActionBar

    protected lateinit var mContext: Context

    protected lateinit var viewBinding: T

    protected abstract val layoutResourceId: Int

    protected val apiRequest: ApiRequest by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        viewBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

    internal fun initActionBar(isBackIcon: Boolean, title: String) {
        coustomActionBar = findViewById(R.id.cv_actionbar)
        coustomActionBar.setChangeIcon(isBackIcon)
        coustomActionBar.setTitle(title)
        coustomActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                setResult(RESULT_CANCELED)
                finish()
            }
        })
        coustomActionBar.setActionBarRightClick(object : IActionBarClick() {
            override fun onRightClick() {
                //searchAPI
                val intent = Intent(mContext, SearchActivity::class.java)
                startActivity(intent)
            }
        })
    }

    internal fun setActionBarTitle(title: String) {
        coustomActionBar.setTitle(title)
    }
}