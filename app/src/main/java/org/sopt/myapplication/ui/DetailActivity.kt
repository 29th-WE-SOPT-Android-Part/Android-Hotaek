package org.sopt.myapplication.ui

import android.os.Bundle
import android.util.Log
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivityDetailBinding
import org.sopt.myapplication.ui.base.BaseActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDetail()


    }


    private fun initDetail() {
        val image = intent.getIntExtra("userImage", 0)
        val name = intent.getStringExtra("userName")
        val introduce = intent.getStringExtra("userIntroduce")
        with(binding) {
            imgDetailProfile.setImageResource(image)
            textDetailName.text = name
            textProfileInputIntroduce.text = introduce
        }
    }
}