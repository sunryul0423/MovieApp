package com.movie.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.`interface`.IActionBarClick
import com.movie.common.constants.FINISH_INTERVAL_TIME
import com.movie.common.constants.PAGER_COUNT
import com.movie.customview.adapter.CustomListAdapter
import com.movie.customview.adapter.UpcomingPagerAdapter
import com.movie.customview.view.CoustomDrawerView
import com.movie.customview.view.CustomIndicator
import com.movie.databinding.ActivityMainBinding
import com.movie.model.view.MainViewModel
import com.movie.model.view.MainViewModelFactory


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private var backPressedTime: Long = 0L

    private lateinit var abToggle: ActionBarDrawerToggle
    private lateinit var cvDrawerView: CoustomDrawerView
    private lateinit var cvIndicator: CustomIndicator
    private lateinit var rvNowScreen: RecyclerView
    private lateinit var rvPopular: RecyclerView
    private lateinit var rvTopRated: RecyclerView

    private lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initActionBar(false, getString(R.string.ab_main_title))

        mainViewModelFactory = MainViewModelFactory(apiRequest, UpcomingPagerAdapter(), CustomListAdapter())
        // 바인딩 뷰-모델 연결
        val mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        viewBinding.mainViewModel = mainViewModel
        viewBinding.lifecycleOwner = this


        setView()
        coustomActionBar.setActionBarLeftClick(object : IActionBarClick() {
            override fun onLeftClick() {
                //슬라이드 메뉴
                viewBinding.dlView.openDrawer(cvDrawerView)
            }
        })
    }

    override fun onPause() {
        viewBinding.dlView.closeDrawer(cvDrawerView)
        super.onPause()
    }


    private fun setView() {
        viewBinding.srlRefreshView.setOnRefreshListener {
            reqeustApi()
            viewBinding.srlRefreshView.isRefreshing = false
        }
        abToggle =
            object : ActionBarDrawerToggle(this, viewBinding.dlView, R.string.ab_main_title, R.string.ab_main_title) {

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    viewBinding.dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        viewBinding.dlView.addDrawerListener(abToggle)
        viewBinding.dlView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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
        if (viewBinding.dlView.isDrawerOpen(cvDrawerView)) {
            viewBinding.dlView.closeDrawer(cvDrawerView)
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