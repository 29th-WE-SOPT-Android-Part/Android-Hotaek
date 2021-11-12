# 1. 이미지

![image](https://user-images.githubusercontent.com/71322949/141426621-db5e5f6a-092e-4c2b-b1da-0a3c90daf00b.png)

![Android-Emulator-Pixel_3_XL_API_28_5554-2021-11-12-16-29-36](https://user-images.githubusercontent.com/71322949/141427948-8e84aae3-57ab-4c09-a230-a82fcd27edc0.gif)

![Android-Emulator-Pixel_3_XL_API_28_5554-2021-11-12-16-34-04](https://user-images.githubusercontent.com/71322949/141428325-040f5b0e-fb0f-46cc-9f8a-3e61d6ff0f89.gif)



## 2. 코드 작성

## LEVEL 1

### (1) ServiceCreator.kt

```Kotlin
object ServiceCreator {
    private const val BASE_URL = "https://asia-northeast3-we-sopt-29.cloudfunctions.net/api/"
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor(),))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideOkHttpClient(
        interceptor: AppInterceptor,
    ): OkHttpClient{
        val logger = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
    }



    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .build()

            return chain.proceed(newRequest)
        }
    }

    val signUpService : SignUpService = retrofit.create(SignUpService::class.java)
    val signInService: SignInService = retrofit.create(SignInService::class.java)
}
```



### (2) SignInService.kt

```Kotlin
interface SignInService {
    @POST("user/login")
    suspend fun getSignIn(
        @Body body : RequestSignInData
    ) : ResponseSignUpData
}
```



### (3) SignUpService.kt

```kotlin
interface SignUpService {
    @POST("user/signup")
    suspend fun getSignUp(
        @Body body: RequestSignUpData
    ): ResponseSignUpData
}
```

### (4) RequestSignData

```Kotlin
data class RequestSignInData(
    val email : String,
    val password : String
)

```

```Kotlin
data class RequestSignUpData(
    val email : String,
    val name: String,
    val password: String
)
```

### (5) ResponseSignUpData

```kotlin
data class ResponseSignUpData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val id: Int,
        val name: String,
        val email: String
    )
}
```



## LEVEL 2

### 2 - 2 okHttp 헤더 중복 

### ServiceCreator.kt

```Kotlin
 private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor(),))
        .addConverterFactory(GsonConverterFactory.create())
        .build()  

private fun provideOkHttpClient(
        interceptor: AppInterceptor,
    ): OkHttpClient{
        val logger = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
    }



    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .build()

            return chain.proceed(newRequest)
        }
    }
```

Interceptor 인터페이스를 상속받은 AppInterceptor 클래스를 만들었다.

그 뒤에 intercept() 메서드를 오버라이딩 받아서 intercept()의 응답으로 온 chain 객체를 이용해 공통 Header를 추가 하였다.



추가로 서버 통신의 Log 값을 자세히 보기 위해 HttpLoggingInterceptor의 객체를 만들었고, 설정한 client로 retrofit client를 설정했다.



## LEVEL 3

```Kotlin
 private fun getSignIn(){
        val requestSignInData = RequestSignInData(
            email = binding.etId.text.toString(),
            password = binding.etPassword.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            runCatching { ServiceCreator.signInService.getSignIn(requestSignInData) }
                .onSuccess {
                    Log.d("서버통신", "성공")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignInActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .onFailure {
                    Log.d("서버통신", "실패")
                }


        }
    }
```

비동기 처리를 위해 코루틴을 사용해서 서버 통신을 진행했다.

CoroutineScope의 CoroutineContext를 IO로 설정하고 launch를 통해 코루틴을 생성헀다. 그뒤 runCatching을 통해 예외 처리를 진행하고 withContext()를 통해서 다시 Main 쓰레드에서 intent 작업을 하도록 코드를 작성했다.

코루틴의 특징 중 중요한 부분은 이 두가지이다.

- 협력형 멀티 태스킹
- 동시성 프로그래밍 지원

코루틴(Co + Routine)에서 Co는 "협력"이라는 의미를 지니고 있고, Routine은 하나의 함수라고 생각하면 된다. 직역하자면 "협력하는 함수"이다.

Routine에 대해 간단히 알아보자면 main routine과 sub routine이 존재한다.

```kotlin
fun main(){
    ...
    val beerCount = drinkBeer(value)
    ...
}

fun drinkBeer(value : Int){
    val one = 1
    val beerCount = vlaue + one
    
    return beerCount
}
```

다음과 같은 코틀린 코드에서 main 함수는 메인이 되는 함수! 

여기서 drinkBeer라는 서브 함수를 호출한다.

메인 함수는 메인 루틴, 서브 함수는 서브 루틴이 된다.

이 서브 루틴의 경우 루틴에 진입하는 곳과 빠져나오는 곳이 명확하게 보인다. 

메인 루틴이 서브 루틴을 호출했을 때, 서브루틴의 맨 처음에 진입하여 return을 호출하거나 닫는 괄호를 만날 때 이 서브 루틴을 빠져나오게 된다.

위의 코드를 예시로 들자면, drinkBeer 함수에 진입하고 return을 통해 빠져나오게 된다.(명확해!)

그러나 코루틴은 다르다.

일단 코루틴도 routine이므로 하나의 함수로 여기자.

위의 서브 루틴과 다르게 코루틴은 진입할 수 있는 곳도 여러 개이고, 함수를 빠져나갈 수 있는 곳도 여러개다.

즉 중간에 나갈 수 있고, 다시 나갔던 그 지점으로 들어올 수도 있다.

</br>

이때 나갈 수 있도록 하는 코드가 **suspend** 코드이다.

추가로 이 suspend를 붙이는 것은 코틀린 컴파일러에게 이 함수가 코루틴 안에서 실행되어야 한다고 알려주는 역할!!

코드로 보자

```Kotlin
val scope = CoroutineScope(Dispatchers.Main)

fun homeWork(){
	scope.launch{
    	writeCode()
        writeReadMe()
        gitCommit()
        gitPush()
    }
}
```
```Kotlin
suspend fun writeCode(){
    delay(2000)
}

suspend fun wrtieReadMe(){
    delay(3000)
}

suspend fun gitCommit(){
    delay(5000)
}

suspend fun gitPush(){
    delay(1500)
}
```

homeWork라는 함수가 있다. 쓰레드의 main 함수가 이 homeWork라는 함수를 호출하면 우리가 만든 코루틴 스코프가 launch라는 코루틴 빌더를 만나서 코루틴을 만들어줍니다.(나중에 따로 설명)

위에서 말했다시피 이 homeWork()는 언제든지 진입할 수 있고 탈출할 수 있게 된다.

코루틴이 실행되고, **suspend** 키워드의 함수를 만나게 되면, (여기서는 writeCode에 해당) 더 이상 그 아래의 코드를 실행하지 않고 homeWork() 라는 코루틴 함수를 탈출한다!

탈출했더라도 메인 쓰레드는 가만히 있지 않고, 다른 코드들을 실행하거나 UI 작업을 진행할 수도 있다. 그렇지만 어디선가 writeCode는 계속 실행되고 있다.(다른 쓰레드에서 실행될 수도 있고, 동시성 프로그래밍으로 작동될 수도 있음)

메인쓰레드가 다른 코드를 실행하고 있다가도, wirteCode() 가 다 실행되었을 때 다시 탈출했던 homeWork() 코루틴으로 돌아오고, 그 아래인 writeReadMe()부터 다시 resume 된다.

### 1) CoroutineScope

> 말 그대로 코루틴의 범위로 코루틴 블록을 묶음으로 제어할 수 있는 단위

이 스코프에서 생성된 코루틴을 계속해서 주시하면서 실행을 취소하거나, 실패할 시에 예외를 처리할 수 있게 해준다.

단, GlobalScope도 쓸 수 있지만 코루틴의 생명주기를 제어하기를 원한다면 권장하지 않는다!

```Kotlin

val scope = CoroutineScope(Dispatchers.Main)

fun homeWork(){
	scope.launch{
    }
```

코루틴의 실행을 멈추기 위해서 cancel()메소드를 활용한다.

```Kotlin
scope.cancel()
```

### 2) CoroutineContext

> 코루틴을 어떻게 처리할 것 인지에 대한 여러가지 정보의 집합

CoroutineContext의 주요 요소로 Job과 Dispathcer가 있다.




### 3) Dispathcer

> 어떤 스레드를 이용해서 어떻게 동작할 것인지를 정의


- IO : 네트워크나 디스크 작업 할 때 사용

- Defalut : CPU 사용량이 많은 작업, 주 스레드에서 작업하기에 너무 긴 작업 들

- Main : UI 작업이나, 쓰레드를 블락하지 않고 빨리 실행되는 작업에 사용

```Kotlin
val scope = CoroutineScope(Dispathcer.IO)
```


### 4) launch & async

> 생성된 스코프에서 launch와 async를 사용하여 코루틴을 생성할 수 있다.

- launch의 경우에는 반환값이 없으며(job), 단순히 그 자리에서 실행시키고 끝남!

- async는 반환값이 존재한다.(Deffered)


이 둘의 공통점은

- 새로운 코루틴을 만든다.

- 하나의 Dispatcher를 가진다.

- 스코프 안에서 실행된다.

- suspend 함수가 아니다.





# 3. 이번 과제를 통해 배운 내용

일단 가장 많이 배웠던 것은 okHttp에 대한 내용이였다. Interceptor라는 것을 처음 공부했고, 반복되는 Header를 계속 집어 넣지 않고 편하게 사용할 수 있도록 하는 방법에 대해 배웠다.

그리고 코루틴도 잠깐 공부했었는데 기존에 서버 통신 할때는 VIewModel의 ViewModelScope만을 사용해서 자세히 공부할 기회가 없었는데 이번 과제를 통해서 어느정도 코루틴에 대해 알 수 있었다고 생각한다.

