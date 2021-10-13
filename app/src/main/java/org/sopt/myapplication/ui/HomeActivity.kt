package org.sopt.myapplication.ui

import org.sopt.myapplication.HomeData



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivityHomeBinding
import org.sopt.myapplication.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnGitHub()
        initHome()
    }



    private fun btnGitHub(){
        binding.btnGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ho-taek"))
            startActivity(intent)
        }
    }

    private fun initHome(){
        binding.user = HomeData("곽호택","25","INFP","만나서 반가워요 안녕하세요!!!",
        R.drawable.ic_android_black_24dp)

    }
}