package org.sopt.myapplication.ui.sign

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.sopt.myapplication.R
import org.sopt.myapplication.data.api.ServiceCreator
import org.sopt.myapplication.data.request.RequestSignInData
import org.sopt.myapplication.databinding.ActivitySigninBinding
import org.sopt.myapplication.ui.HomeActivity
import org.sopt.myapplication.ui.base.BaseActivity
import org.sopt.myapplication.util.SoptSharedPreference

class SignInActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickLogin()
        clickSignUp()
        autoLogin()
    }

    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val id = result.data?.getStringExtra("id")
                    val password = result.data?.getStringExtra("password")
                    binding.etId.setText(id)
                    binding.etPassword.setText(password)
                }

            }

    }


    private fun clickLogin() {
        with(binding) {
            btnLogin.setOnClickListener {
                if (etId.text.isNullOrBlank() || etPassword.text.isNullOrBlank()) {
                    Toast.makeText(this@SignInActivity, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    getSignIn()
                }

            }
        }
    }

    private fun getSignIn() {
        SoptSharedPreference.setAutoLogin(this, true)
        Toast.makeText(this@SignInActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun clickSignUp() {
        binding.textSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun autoLogin(){
        val auto = SoptSharedPreference.getAutoLogin(this)
        if(auto){
            Toast.makeText(this, "자동 로그인 성공!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}