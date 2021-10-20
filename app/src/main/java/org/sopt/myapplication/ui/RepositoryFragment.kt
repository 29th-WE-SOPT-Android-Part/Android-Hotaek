package org.sopt.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.myapplication.R
import org.sopt.myapplication.data.RepositoryData
import org.sopt.myapplication.databinding.FragmentRepositoryBinding
import org.sopt.myapplication.ui.adapter.RepositoryAdapter
import org.sopt.myapplication.ui.base.BaseFragment


class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>(R.layout.fragment_repository) {
    private lateinit var repositoryAdapter : RepositoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRepository()

    }


    private fun initRepository(){
        repositoryAdapter = RepositoryAdapter()
        binding.recyclerRepository.adapter = repositoryAdapter
        repositoryAdapter.setRepository(
            listOf(
                RepositoryData("곽호택", "안드로이드 OB로 최선을 다합시다~~~~~더해더해"),
                RepositoryData("곽호택", "차로를 만나서 너무 행복한 한해였습니다!! "),
                RepositoryData("곽호택", "첫 동아리 솝트 25년 인생 최고의 선택"),
                RepositoryData("곽호택", "안드로이드몬스터 불꽃 카리스마")
            )
        )
    }
}