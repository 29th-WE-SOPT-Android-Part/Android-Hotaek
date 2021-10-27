package org.sopt.myapplication.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.FragmentProfileBinding
import org.sopt.myapplication.ui.base.BaseFragment


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickChangeButton()
    }



    private fun clickChangeButton(){
        childFragmentChange(R.id.fragment_container_recycler, FollowerFragment())
        binding.btnFollower.setOnClickListener {
            childFragmentChange(R.id.fragment_container_recycler, FollowerFragment())
        }

        binding.btnRepository.setOnClickListener {
            childFragmentChange(R.id.fragment_container_recycler,RepositoryFragment())
        }
    }


    private fun childFragmentChange(layoutRes:Int, fragment:Fragment){
        childFragmentManager.beginTransaction()
            .replace(layoutRes, fragment)
            .commit()
    }
}