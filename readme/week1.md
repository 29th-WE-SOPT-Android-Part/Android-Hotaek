## 코드설명 - LEVEL 1

1. SignInActivity

   ```kotlin
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
           startActivity(intent)
       }
   }
   ```

   - clickLogin()

     : 로그인 버튼을 눌렀을 때 토스트 메세지가 나오도록

   - clickSignUp()

     : intent로 회원가입 화면으로 넘어가도록

     

2. SignUpActivity

   ```kotlin
    private fun clickSignUp(){
           with(binding){
               btnSignup.setOnClickListener {
                   if(etSignupId.length() == 0 || etSignupName.length() == 0 || etSingupPassword.length() == 0){
                       Toast.makeText(this@SignUpActivity, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                   } else {
                       finish()
                   }
               }
   
           }
   ```

   이름, 아이디, 비밀번호 중 입력하지 않은 정보가 있는 경우 토스트 메세지를 띄어주도록 함.

   

   ## 2.실행 영상

   <video src="C:\Users\kht07\Videos\Captures\Android Emulator - Pixel_3_XL_API_28_5554 2021-10-10 15-03-53.mp4"></video>



# 코드 설명 LEVEL - 2

### 2 - 1

- SignUp()

```kotlin
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
```

- SignIn()

  ``` kotlin
  private lateinit var resultLauncher: ActivityResultLauncher<Intent> 
  
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
  
  
  
   private fun clickSignUp(){
          binding.btnSignUp.setOnClickListener {
              val intent = Intent(this, SignUpActivity::class.java)
              resultLauncher.launch(intent)
          }
      }
  ```



### 2 - 2

- 명시적 인텐트 : 일반적으로 본인의 앱 내부에서 구성 요소를 시작할 때 사용한다. 우리가 SignUp에서 SignIn 액티비티로 넘어갈 때 명시적 인텐트를 사용한다. 즉 명확하게 액티비티 및 서비스의 이름을 알고 있을 때 사용이 가능하다.



- 암시적 인텐트 : 특정 구성 요소의 이름을 사용하지는 않지만, 그 대신에 수행할 일반적인 작업을 선언하고, 또 다른 앱의 구성 요소가 이를 처리할 수 있도록 해주는 것!!

  예를 들면, 우리가 사용자에게 지도의 한 위치를 표시해주고자 하는 경우, 암시적 인텐트를 활용해서 해당 기능을 가진 앱이 지정된 위치를 지도에 표시하도록 요청하는 것을 말한다.

  ```kotlin
  private fun btnGitHub(){
          binding.btnGithub.setOnClickListener {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ho-taek"))
              startActivity(intent)
          }
  
      }
  ```

  



## 3. 느낀점

백스택 관리 하면서 화면 전환 시에 finish()가 아닌 transaction을 사용했었는데 이번 과제를 하면서 백스택 관리 부분에서 finish()를 활용해야겠다고 새롭게 배웠습니다!