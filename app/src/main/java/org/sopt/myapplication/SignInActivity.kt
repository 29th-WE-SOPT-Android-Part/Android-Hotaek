package org.sopt.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.sopt.myapplication.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySigninBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickLogin()
        clickSignUp()
        setResultSignUp()

    }

    private fun setResultSignUp(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val id = result.data?.getStringExtra("id")
                val password = result.data?.getStringExtra("password")
                binding.etId.setText(id)
                binding.etPassword.setText(password)
            }

        }

    }



    private fun clickLogin(){
        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "곽호택님 환영합니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickSignUp(){
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}