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

   <img src="https://user-images.githubusercontent.com/71322949/137090899-6d40da30-9398-4f80-b777-25b0fccc474a.gif"></image>



## 2. LEVEL 2

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





## 코드 설명 LEVEL 3

### 3-1 : VIewBinding, DataBinding

```kotlin
plugin{
 id 'kotlin-kapt'
}

 buildFeatures{
        viewBinding = true
        dataBinding = true
    }
```

plugin에 kotlin-kapt를 추가해주고 buildFeatures밑에 databinding = true를 넣어주었다.



``` kotlin
data class HomeData(
    val name : String,
    val age : String,
    val mbti : String,
    val introduce : String,
    val image: Int
)
```

다음과 같이 HomeData라는 데이터 클래스를 만들어 줬고,

```kotlin
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
<data>
        <variable
            name="user"
            type="org.sopt.myapplication.HomeData" />
    </data>

...
</layout>
```

activity_home.xml 파일 위에 layout 태그로 감싸주고 그 아래에 

data 태그 안에 <variable로 name과 type을 묶어주었다.

name에는 내가 사용할 변수 이름을 적어줬고

type에는 데이터가 있는 경로를 적어줬다.



그리고 HomeActivity 코드 단에서 다음과 같이 데이터를 넣어줬다.

```kotlin
private fun initHome(){
        binding.user = HomeData("곽호택","25","INFP","만나서 반가워요 안녕하세요!!!",
        R.drawable.ic_android_black_24dp)

    }
```

- 데이터 바인딩이란

  지금까지 내가 사용해왔던 ViewBinding의 경우 뷰에 텍스트를 띄우기 위해서 코틀린 파일에서 아래와 같이 작성해야 했다.

  ```kotli
  binding.tvHello.text = "곽호택"
  ```

  하지만 DataBinding의 경우에는 코틀린 파일이 아닌 xml에서 다음과 같이 바로 데이터를 연결시켜 뷰에 띄울 수 있다.

```kotli
<TextView
	android:text = "@{user.name}"
```

이런 방식으로 xml에 코드를 넣을 경우 액티비티에는 로직을 위한 코드만 남게 되어 더욱 코드가 간결해질 수 있다.



그렇다면 ViewBinding 하고 DataBinding 중에 어떤 상황에 뭘 쓰면 좋을까? 무조건 DataBinding이 좋을까?
No!!!

![img](https://media.vlpt.us/images/ho-taek/post/fff22d29-8bf4-478b-af08-c9b9ebacf1f4/image.png)



ViewBinding이 DataBinding에 속해 있다는 것은 DataBinding이 ViewBinding의 역할을 그대로 수행할 수 있음을 보여준다.
추가로 양방향 결합도 가능하고 xml 파일의 변수도 사용한다.

ViewBinding의 경우 DataBinding과 비교했을 때 간단하고 용량이 작기 때문에 빌드 속도에서 DataBinding에 비해 빠르기 때문에 상황에 맞게 적절히 사용하는 것이 중요하다.





### 3-2 : setOnClickListener

코틀린에서 추상 메서드 하나를 인수로 사용할 때 함수를 인수로 전달하면 편하다.

만약 자바로 작성된 메서드가 하나인 인터페이스를 구현할 때는 대신 함수를 작성할 수 있는데 이를 **SAM변환**이라고 한다.

``` kotlin
button.setOnClickListener(object: View.OnClickListener){
    override fun onClick(v: VIew?){
        
    }
}
```

위의 코드에서 View.OnClickListener라는 인터페이스에 onClick()이라는 추상 메서드가 하나 있다. 이때 SAM변환을 통해 람다식으로 변경할 수 있다.

``` kotlin
button.setOnClickListener({ v: View? ->
                          })
```



코틀린의 규칙에 따라 함수의 마지막 매개변수가 함수일 경우 해당 인수로 전달된 람다 표현식을 괄호 밖에 배치 할 수 있는데

이를 **후행 람다 전달** 이라고 한다.

```kotlin
button.setOnCLickListener() { v ->

}
```



추가로 컴파일러가 자료형을 추론할 수 있는 경우 생략이 가능하다.

```kotlin
button.setOnCLickListener(){
    //코드
}
```



결국 위와 같이 우리가 setOnClickListener()를 편하게 사용할 수 있는 것이다.



## 3. 느낀점

평소 사용했을때 setOnClickListener()를 아무 생각없이 사용했었는데 과제를 통해 새롭게 왜 사용이 가능한지에 대해서 알게 됐고, Activity에서 이동할때 finish가 아닌 intent만을 이용해서 화면 전환을 했었는데, finish라는 것을 새롭게 배웠습니다!! 그리고 registerforActivityResult 사용법에 대해서 익혔고, contracts의 종류가 되게 많고, 이를 나중에 카메라 권한을 얻을 때 사용하면 되겠다 라는 점에서 좋은 공부였습니다!