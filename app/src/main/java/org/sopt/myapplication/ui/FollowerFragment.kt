package org.sopt.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.myapplication.R
import org.sopt.myapplication.data.FollowData
import org.sopt.myapplication.databinding.FragmentFollowerBinding
import org.sopt.myapplication.ui.adapter.FollowAdapter
import org.sopt.myapplication.ui.base.BaseFragment


class FollowerFragment : BaseFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {
    private lateinit var followAdapter: FollowAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFollow()

    }


    private fun initFollow(){
        followAdapter = FollowAdapter()
        binding.recyclerFollow.adapter = followAdapter
        followAdapter.setFollowData(
            listOf(
                FollowData(R.drawable.hotaek, "곽호택", "안드로이드 OB"),
                FollowData(R.drawable.hotaek, "곽호택", "차로 갓갓갓갓"),
                FollowData(R.drawable.hotaek, "곽호택", "솝트 갓갓갓갓")
            )
        )
    }
}