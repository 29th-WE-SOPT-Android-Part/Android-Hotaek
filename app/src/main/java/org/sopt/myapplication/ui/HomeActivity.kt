package org.sopt.myapplication.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import org.sopt.myapplication.HomeData
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivityHomeBinding
import org.sopt.myapplication.ui.base.BaseActivity
import org.sopt.myapplication.util.changeFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goFragment()
        initHome()
    }




    private fun initHome(){
        binding.user = HomeData("곽호택","25","INFP","만나서 반가워요 안녕하세요!!!",
        R.drawable.hotaek)

        changeFragment(R.id.fragment_container_recycler, FollowerFragment())
    }

    private fun goFragment(){
        with(binding){
            btnFollower.setOnClickListener {
                changeFragment(R.id.fragment_container_recycler, FollowerFragment())
            }

            btnRepository.setOnClickListener {
                changeFragment(R.id.fragment_container_recycler, RepositoryFragment())
            }
        }

    }
}