## 코드설명

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





## 3. 느낀점

백스택 관리 하면서 화면 전환 시에 finish()가 아닌 transaction을 사용했었는데 이번 과제를 하면서 백스택 관리 부분에서 finish()를 활용해야겠다고 새롭게 배웠습니다!