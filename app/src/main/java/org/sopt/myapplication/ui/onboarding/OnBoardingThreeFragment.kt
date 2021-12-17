package org.sopt.myapplication.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.FragmentOnBoardingOneBinding
import org.sopt.myapplication.databinding.FragmentOnBoardingThreeBinding
import org.sopt.myapplication.ui.base.BaseFragment
import org.sopt.myapplication.ui.sign.SignInActivity


class OnBoardingThreeFragment : BaseFragment<FragmentOnBoardingThreeBinding>(R.layout.fragment_on_boarding_three) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext()
    }


    private fun btnNext(){
        binding.btnOnBoardingThree.setOnClickListener{
            val intent = Intent(requireActivity(), SignInActivity::class.java)
            startActivity(intent)
        }
    }
}