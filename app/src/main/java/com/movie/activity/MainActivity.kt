package com.movie.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.movie.R
import com.movie.interfaces.IActionBarClick
import com.movie.common.constants.FINISH_INTERVAL_TIME
import com.movie.common.constants.PAGER_COUNT
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.customview.view.CustomIndicator
import com.movie.databinding.ActivityMainBinding
import com.movie.model.view.MainViewModel
import com.movie.model.view.MainViewModelFactory


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private var backPressedTime: Long = 0L

    private lateinit var abToggle: ActionBarDrawerToggle
    private lateinit var cvIndicator: CustomIndicator

    private lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initActionBar(false, getString(R.string.ab_main_title))

        mainViewModelFactory = MainViewModelFactory(apiRequest, UpcomingPagerAdapter())
        // 바인딩 뷰-모델 연결
        val mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        viewBinding.mainViewModel = mainViewModel
        viewBinding.lifecycleOwner = this

        setView()
        coustomActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                //슬라이드 메뉴
                viewBinding.dlView.openDrawer(viewBinding.cvDrawerView)
            }
        })
    }

    override fun onPause() {
        viewBinding.dlView.closeDrawer(viewBinding.cvDrawerView)
        super.onPause()
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

        cvIndicator = findViewById(R.id.cv_indicator)
        //원사이의 간격
        cvIndicator.setItemMargin(10)
        //애니메이션 속도
        cvIndicator.setAnimDuration(300)
        //indecator 생성
        cvIndicator.createDotPanel(
            PAGER_COUNT,
            R.drawable.indicator_paging_dot_on,
            R.drawable.indicator_paging_dot
        )
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
                Toast.makeText(mContext, getString(R.string.app_finish_msg), Toast.LENGTH_SHORT).show()
            }
        }
    }
}