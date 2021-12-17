package org.sopt.myapplication.ui.sign

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import org.sopt.myapplication.R
import org.sopt.myapplication.data.api.ServiceCreator
import org.sopt.myapplication.data.request.RequestSignUpData
import org.sopt.myapplication.databinding.ActivitySignUpBinding
import org.sopt.myapplication.ui.base.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickSignUp()
    }


    private fun clickSignUp() {
            binding.btnSignup.setOnClickListener {
                if (binding.etSignupId.text.isNullOrBlank() || binding.etSignupName.text.isNullOrBlank() || binding.etSingupPassword.text.isNullOrBlank()) {
                    Toast.makeText(this@SignUpActivity, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    getSignUp()
                }
            }

        }


    private fun getSignUp() {
        val requestSignUpData = RequestSignUpData(
            email = binding.etSignupId.text.toString(),
            name = binding.etSignupName.text.toString(),
            password = binding.etSingupPassword.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            runCatching { ServiceCreator.signUpService.getSignUp(requestSignUpData) }
                .onSuccess {
                    Log.d("서버통신", "성공")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignUpActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .onFailure {
                    Log.d("서버통신", "실패")
                }


        }

    }
}

