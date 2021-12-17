package org.sopt.myapplication.ui.setting

import android.os.Bundle
import android.widget.Toast
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivitySettingBinding
import org.sopt.myapplication.ui.base.BaseActivity
import org.sopt.myapplication.util.SoptSharedPreference

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnAutoLogin()
    }


    private fun btnAutoLogin(){
        binding.btnAutoLoginRemove.setOnClickListener{
            Toast.makeText(this, "자동 로그인 해제", Toast.LENGTH_SHORT).show()
            SoptSharedPreference.removeAutoLogin(this)
        }

    }
}