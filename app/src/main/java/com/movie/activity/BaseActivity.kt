package com.movie.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.movie.R
import com.movie.customview.view.CustomActionBar
import com.movie.dialog.ProgressDialog
import com.movie.interfaces.IActionBarClick

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var customActionBar: CustomActionBar
    protected lateinit var viewBinding: T
    protected abstract val layoutResourceId: Int
    protected lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = ProgressDialog(this)
        viewBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

    internal fun initActionBar(isBackIcon: Boolean, title: String) {
        customActionBar = findViewById(R.id.cv_actionbar)
        customActionBar.setChangeIcon(isBackIcon)
        customActionBar.setTitle(title)
        customActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                setResult(RESULT_CANCELED)
                finish()
            }
        })
        customActionBar.setActionBarRightClick(object : IActionBarClick() {
            override fun onRightClick() {
                //searchAPI
                val intent = Intent(this@BaseActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        })
    }

    internal fun setActionBarTitle(title: String) {
        customActionBar.setTitle(title)
    }
}