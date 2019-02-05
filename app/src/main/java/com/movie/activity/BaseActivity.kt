package com.movie.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.movie.R
import com.movie.`interface`.IActionBarClick
import com.movie.customview.view.CoustomActionBar
import com.movie.model.request.ApiRequest
import com.movie.server.RetrofitManager
import com.movie.server.RxResponseManager

open class BaseActivity : AppCompatActivity() {

    protected lateinit var rxResponseManager: RxResponseManager

    protected lateinit var apiRequest: ApiRequest

    protected lateinit var coustomActionBar: CoustomActionBar

    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        rxResponseManager = RxResponseManager(mContext)
        apiRequest = RetrofitManager.creare(ApiRequest::class.java)
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

    override fun onDestroy() {
        rxResponseManager.dispose()
        super.onDestroy()
    }
}