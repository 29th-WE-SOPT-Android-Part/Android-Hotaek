package org.sopt.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.myapplication.R
import org.sopt.myapplication.databinding.ActivitySignUpBinding
import org.sopt.myapplication.ui.base.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickSignUp()
    }


    private fun clickSignUp(){
        with(binding){
            btnSignup.setOnClickListener {
                if(etSignupId.text.isNullOrBlank() || etSignupName.text.isNullOrBlank() || etSingupPassword.text.isNullOrBlank()){
                    Toast.makeText(this@SignUpActivity, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    intent.putExtra("id", etSignupId.text.toString())
                    intent.putExtra("password", etSingupPassword.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }

        }
    }


}