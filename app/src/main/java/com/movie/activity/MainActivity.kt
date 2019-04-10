package com.movie.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.movie.R
import com.movie.`interface`.IActionBarClick
import com.movie.`interface`.IRxResult
import com.movie.common.constants.FINISH_INTERVAL_TIME
import com.movie.common.constants.NOW_PALY_PAGER_COUNT
import com.movie.common.constants.PAGER_COUNT
import com.movie.common.constants.POPULAR_COUNT
import com.movie.common.utils.CommonUtil
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.customview.view.CoustomDrawerView
import com.movie.customview.view.CustomIndicator
import com.movie.databinding.ActivityMainBinding
import com.movie.model.data.MovieMainResponse


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private var backPressedTime: Long = 0L

    private lateinit var srlRefreshView: SwipeRefreshLayout
    private lateinit var dlView: DrawerLayout
    private lateinit var abToggle: ActionBarDrawerToggle
    private lateinit var cvDrawerView: CoustomDrawerView
    private lateinit var cvIndicator: CustomIndicator
    private lateinit var rvNowScreen: RecyclerView
    private lateinit var rvPopular: RecyclerView
    private lateinit var rvTopRated: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initActionBar(false, getString(R.string.ab_main_title))

        setView()
        coustomActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                //슬라이드 메뉴
                dlView.openDrawer(cvDrawerView)
            }
        })
    }

    override fun onPause() {
        dlView.closeDrawer(cvDrawerView)
        super.onPause()
    }


    private fun setView() {
        srlRefreshView = findViewById(R.id.srl_refresh_view)
        srlRefreshView.setOnRefreshListener {
            reqeustApi()
            srlRefreshView.isRefreshing = false
        }
        dlView = findViewById(R.id.dl_view)
        abToggle = object : ActionBarDrawerToggle(this, dlView, R.string.ab_main_title, R.string.ab_main_title) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        dlView.addDrawerListener(abToggle)
        dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        cvDrawerView = findViewById(R.id.cv_drawer_view)

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

        rvNowScreen = findViewById(R.id.rv_now_screen)
        rvPopular = findViewById(R.id.rv_popular)
        rvTopRated = findViewById(R.id.rv_top_rated)
        //api
        reqeustApi()
    }

    private fun reqeustApi() {
        //TODO: RxJava와 Retrofit 사용시 getUpcoming() 리턴을 Observable

//        rxResponseManager.add(apiRequest.getUpcoming(CommonUtil.getParam()), object : IRxResult {
//
//            override fun <T> onNext(response: T) {
//
//                val upcomingPagerAdapter = UpcomingPagerAdapter(mContext, movieList)
//                viewBinding.vpUpcoming.adapter = upcomingPagerAdapter
//                vpUpcoming.offscreenPageLimit = movieList.size
//                vpUpcoming.currentItem = 0
//                vpUpcoming.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//                    override fun onPageScrollStateChanged(state: Int) {
//                    }
//
//                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                    }
//
//                    override fun onPageSelected(position: Int) {
//                        cvIndicator.selectDot(position)
//                    }
//                })
//            }
//
//            override fun onErrer(error: Throwable) {
//            }
//        })
//
//        rxResponseManager.add(apiRequest.getNowPlaying(CommonUtil.getParam()), object : IRxResult {
//
//            override fun <T> onNext(response: T) { //라이브 데이터
//                var movieList: List<MovieMainResponse.Movie> = (response as MovieMainResponse).results
//                    ?: emptyList()
//                if (NOW_PALY_PAGER_COUNT < movieList.size) {
//                    movieList = movieList.subList(0, NOW_PALY_PAGER_COUNT)
//                }
//
//                val nowPlayAdapter = CustomListAdapter(mContext, true, movieList)
//                rvNowScreen.adapter = nowPlayAdapter
//                rvNowScreen.setHasFixedSize(true)
//                val layoutManager = LinearLayoutManager(mContext)
//                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                rvNowScreen.layoutManager = layoutManager
//            }
//
//            override fun onErrer(error: Throwable) {
//            }
//        })
//
//        rxResponseManager.add(apiRequest.getPopular(CommonUtil.getParam()), object : IRxResult {
//
//            override fun <T> onNext(response: T) {
//                var movieList: List<MovieMainResponse.Movie> = (response as MovieMainResponse).results
//                    ?: emptyList()
//                if (POPULAR_COUNT < movieList.size) {
//                    movieList = movieList.subList(0, POPULAR_COUNT)
//                }
//
//                val popularAdapter = CustomListAdapter(mContext, false, movieList)
//                rvPopular.adapter = popularAdapter
//                rvPopular.setHasFixedSize(true)
//                val layoutManager = LinearLayoutManager(mContext)
//                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                rvPopular.layoutManager = layoutManager
//
//            }
//
//            override fun onErrer(error: Throwable) {
//            }
//        })
//
//        rxResponseManager.add(apiRequest.getTopRated(CommonUtil.getParam()), object : IRxResult {
//
//            override fun <T> onNext(response: T) {
//                var movieList: List<MovieMainResponse.Movie> = (response as MovieMainResponse).results
//                    ?: emptyList()
//                if (POPULAR_COUNT < movieList.size) {
//                    movieList = movieList.subList(0, POPULAR_COUNT)
//                }
//
//                val topRatedAdapter = CustomListAdapter(mContext, false, movieList)
//                rvTopRated.adapter = topRatedAdapter
//                rvTopRated.setHasFixedSize(true)
//                val layoutManager = LinearLayoutManager(mContext)
//                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                rvTopRated.layoutManager = layoutManager
//            }
//
//            override fun onErrer(error: Throwable) {
//            }
//        })
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
        if (dlView.isDrawerOpen(cvDrawerView)) {
            dlView.closeDrawer(cvDrawerView)
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