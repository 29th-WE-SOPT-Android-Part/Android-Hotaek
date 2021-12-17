package org.sopt.myapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.FragmentHomeBinding
import org.sopt.myapplication.ui.adapter.HomeFragmentViewPagerAdapter
import org.sopt.myapplication.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var homeFragmentViewPagerAdapter: HomeFragmentViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initTab()
    }



    private fun initAdapter(){
        val fragmentList = listOf(HomeFollowingFragment(),HomeFollowerFragment())

        homeFragmentViewPagerAdapter = HomeFragmentViewPagerAdapter(requireActivity())
        homeFragmentViewPagerAdapter.fragments.addAll(fragmentList)

        binding.vpHomeTab.adapter = homeFragmentViewPagerAdapter
    }

    private fun initTab(){
        val tabLabel = listOf("첫 번째", "두 번째")

        TabLayoutMediator(binding.tlHome, binding.vpHomeTab) {tab, position ->
            tab.text = tabLabel[position]
        }.attach()
    }
}