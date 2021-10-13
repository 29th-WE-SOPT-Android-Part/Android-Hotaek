package org.sopt.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.sopt.myapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickSignUp()
    }


    private fun clickSignUp(){
        with(binding){
            btnSignup.setOnClickListener {
                if(etSignupId.length() == 0 || etSignupName.length() == 0 || etSingupPassword.length() == 0){
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