package com.movie.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.movie.R
import com.movie.util.FINISH_INTERVAL_TIME
import com.movie.util.PAGER_COUNT
import com.movie.util.showThrowableToast
import com.movie.util.showToast
import com.movie.databinding.ActivityMainBinding
import com.movie.interfaces.IActionBarClick
import com.movie.model.view.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId
        get() = R.layout.activity_main

    private var backPressedTime = 0L

    private lateinit var abToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar(false, getString(R.string.ab_main_title))
        //indicator 생성
        viewBinding.cvIndicator.createDotPanel(
            PAGER_COUNT,
            R.drawable.indicator_dot_off,
            R.drawable.indicator_dot_on
        )

        // 바인딩 뷰-모델 연결
        val mainViewModel: MainViewModel by viewModel()
        mainViewModel.setIndicator(viewBinding.cvIndicator)
        viewBinding.mainViewModel = mainViewModel
        viewBinding.lifecycleOwner = this
        customActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                //슬라이드 메뉴
                viewBinding.dlView.openDrawer(viewBinding.cvDrawerView)
            }
        })

        liveDataObserver(mainViewModel)
        setView()
    }

    override fun onPause() {
        viewBinding.dlView.closeDrawer(viewBinding.cvDrawerView)
        super.onPause()
    }

    private fun liveDataObserver(mainViewModel: MainViewModel) {
        mainViewModel.getProgress().observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.cancel()
        })
        mainViewModel.getThrowableData().observe(this, Observer {
            showThrowableToast(this, it)
        })
    }

    private fun setView() {
        abToggle =
            object : ActionBarDrawerToggle(this, viewBinding.dlView, R.string.ab_main_title, R.string.ab_main_title) {
                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    viewBinding.dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        viewBinding.dlView.addDrawerListener(abToggle)
        viewBinding.dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        abToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        abToggle.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime
        if (viewBinding.dlView.isDrawerOpen(viewBinding.cvDrawerView)) {
            viewBinding.dlView.closeDrawer(viewBinding.cvDrawerView)
        } else {
            if (intervalTime in 0..FINISH_INTERVAL_TIME) {
                super.onBackPressed()
            } else {
                backPressedTime = currentTime
                showToast(this, getString(R.string.app_finish_msg))
            }
        }
    }
}