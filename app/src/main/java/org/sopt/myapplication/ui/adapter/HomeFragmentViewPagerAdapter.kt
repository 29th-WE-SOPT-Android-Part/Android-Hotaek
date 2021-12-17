package org.sopt.myapplication.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeFragmentViewPagerAdapter(fragment:FragmentActivity) :
    FragmentStateAdapter(fragment){

    val fragments = mutableListOf<Fragment>()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}