package org.sopt.myapplication.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.FragmentOnBoardingOneBinding
import org.sopt.myapplication.ui.base.BaseFragment


class OnBoardingOneFragment : BaseFragment<FragmentOnBoardingOneBinding>(R.layout.fragment_on_boarding_one) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext()
    }



    private fun btnNext(){
        binding.btnOnBoardingOne.setOnClickListener{
            findNavController().navigate(R.id.action_onBoardingOneFragment_to_onBoardingTwoFragment)
        }
    }
}