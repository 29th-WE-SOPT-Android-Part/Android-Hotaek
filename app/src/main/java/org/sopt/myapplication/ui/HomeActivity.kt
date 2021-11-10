package org.sopt.myapplication.ui


import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import org.sopt.myapplication.HomeData
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivityHomeBinding
import org.sopt.myapplication.ui.adapter.HomeViewPagerAdapter
import org.sopt.myapplication.ui.profile.FollowerFragment
import org.sopt.myapplication.ui.base.BaseActivity
import org.sopt.myapplication.ui.camera.CameraFragment
import org.sopt.myapplication.ui.home.HomeFragment
import org.sopt.myapplication.ui.profile.ProfileFragment
import org.sopt.myapplication.ui.profile.RepositoryFragment
import org.sopt.myapplication.util.changeFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private lateinit var homeViewPagerAdatper : HomeViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()

        initBottomNavigation()
    }

    private fun initViewPager(){
        val fragmentList = listOf(ProfileFragment(), HomeFragment(), CameraFragment())

        homeViewPagerAdatper = HomeViewPagerAdapter(this)
        homeViewPagerAdatper.fragments.addAll(fragmentList)
        binding.vpHome.adapter = homeViewPagerAdatper
    }


    private fun initBottomNavigation() {
        with(binding) {
            vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bnvHome.menu.getItem(position).isChecked = true
                }
            })

            bnvHome.setOnItemSelectedListener{
                when(it.itemId){
                    R.id.navigation_menu_profile -> {
                        vpHome.currentItem = FIRST_FRAGMENT
                        return@setOnItemSelectedListener true
                    }

                    R.id.navigation_menu_home -> {
                        vpHome.currentItem = SECOND_FRAGMENT
                        return@setOnItemSelectedListener  true
                    }

                    else -> {
                        vpHome.currentItem = THIRD_FRAGMENT
                        return@setOnItemSelectedListener  true
                    }
                }
            }
        }

    }


    companion object{
        const val FIRST_FRAGMENT = 0
        const val SECOND_FRAGMENT = 1
        const val THIRD_FRAGMENT = 2
    }

}
