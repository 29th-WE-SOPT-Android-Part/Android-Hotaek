# 1. 영상

(1) 온보딩

![Android Emulator - Pixel_3_XL_API_28_5554 2021-12-17 19-08-15](https://user-images.githubusercontent.com/71322949/146528149-c70a07b3-e8db-437e-8643-d40eed0a2a1d.gif)





![Android Emulator - Pixel_3_XL_API_28_5554 2021-12-17 19-10-41](https://user-images.githubusercontent.com/71322949/146528346-3b95c690-2a1c-421b-bc6f-53e56f2f8569.gif)



![Android Emulator - Pixel_3_XL_API_28_5554 2021-12-17 19-11-57](https://user-images.githubusercontent.com/71322949/146528669-6438ed32-7be9-4923-a36b-ec0569805372.gif)





## 2. 코드 작성

## LEVEL 1

### 1) onBoarding

(1) onBoardingOneFragment.kt

```Kotlin
class OnBoardingOneFragment : BaseFragment<FragmentOnBoardingOneBinding>(R.layout.fragment_on_boarding_one) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext()
    }



    private fun btnNext(){
        binding.btnOnBoardingOne.setOnClickListener{
            findNavController().navigate(R.id.action_onBoardingOneFragment_to_onBoardingTwoFragment)
        }
    }
}
```

(2) nav_sopt.xml

```kotlin
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_sopt"
    app:startDestination="@id/onBoardingOneFragment">

    <fragment
        android:id="@+id/onBoardingOneFragment"
        android:name="org.sopt.myapplication.ui.onboarding.OnBoardingOneFragment"
        android:label="OnBoardingOneFragment" >
        <action
            android:id="@+id/action_onBoardingOneFragment_to_onBoardingTwoFragment"
            app:destination="@id/onBoardingTwoFragment" />
    </fragment>
    <fragment
        android:id="@+id/onBoardingTwoFragment"
        android:name="org.sopt.myapplication.ui.onboarding.OnBoardingTwoFragment"
        android:label="OnBoardingTwoFragment" >
        <action
            android:id="@+id/action_onBoardingTwoFragment_to_onBoardingThreeFragment"
            app:destination="@id/onBoardingThreeFragment" />
    </fragment>
    <fragment
        android:id="@+id/onBoardingThreeFragment"
        android:name="org.sopt.myapplication.ui.onboarding.OnBoardingThreeFragment"
        android:label="OnBoardingThreeFragment" />
</navigation>
```



### 2) SharedPreference

(1) SoptSharedPreference.kt

```kotlin
object SoptSharedPreference {

    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"

    fun getAutoLogin(context: Context) : Boolean{
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        return preferences.getBoolean(AUTO_LOGIN, false)
    }


    fun setAutoLogin(context: Context, value : Boolean){
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .putBoolean(AUTO_LOGIN, value)
            .apply()
    }


    fun removeAutoLogin(context: Context){
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .remove(AUTO_LOGIN)
            .apply()
    }
}
```



### 3) Util 패키지

![image](https://user-images.githubusercontent.com/71322949/146525780-b6c05682-8f07-4627-86af-17395d71e009.png)

(1) BindingAdapter.kt

```
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageBind")
    fun setImage(imageView: ImageView, imageUrl: Int) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .circleCrop()
            .into(imageView)

    }

}
```

xml단에서 glide를 적용하기 위해 object의 싱글톤 형태로 BIndingAdapter를 만들었다!

그리고 

(2) MyDecoration.kt

```kotlin
class MyDecoration(
    private val dividerHeight : Int,
    private val dividerColor: Int
): RecyclerView.ItemDecoration() {
    private val paint = Paint()
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        myDivider(c, parent, color = dividerColor )
    }


    private fun myDivider(c:Canvas, parent:RecyclerView, color: Int){
        paint.color = color


        for (i in 0 until parent.childCount){
            //뷰 객체 가져오기
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + param.bottomMargin
            val dividerBottom = dividerTop + dividerHeight

            c.drawRect(
                child.left.toFloat(),
                dividerTop.toFloat(),
                child.right.toFloat(),
                dividerBottom.toFloat(),
                paint
            )

        }
        }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = dividerHeight
    }
}
```

(3) NestedScrollbaleHost.kt

```kotlin
class NestedScrollableHost : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    private val child: View? get() = if (childCount > 0) getChildAt(0) else null

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            0 -> child?.canScrollHorizontally(direction) ?: false
            1 -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = parentViewPager?.orientation ?: return

       
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL

            
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                   
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                   
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                       
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}
```

(4)SoptSharedPreference.kt

```kotlin
object SoptSharedPreference {

    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"

    fun getAutoLogin(context: Context) : Boolean{
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        return preferences.getBoolean(AUTO_LOGIN, false)
    }


    fun setAutoLogin(context: Context, value : Boolean){
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .putBoolean(AUTO_LOGIN, value)
            .apply()
    }


    fun removeAutoLogin(context: Context){
        val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
        preferences.edit()
            .remove(AUTO_LOGIN)
            .apply()
    }


}
```



UI나 data에 직접적으로 관련 된 것이 아닌 간접적으로 기능을 제공 해주는 클래스들을 Util 패키지 안에 넣어줬다.

![image](https://user-images.githubusercontent.com/71322949/146527390-3e70c590-a157-44bf-8f92-76178f6bc63c.png)



data패키지 안에 api 및 request , response 모델을 넣었고 ui안에는 패키지 이름과 같은 activity 및 Fragment를 넣어줬고 ui 패키지 안에 adapter 패키지를 따로 집어넣어서 저 안에 adapter를 넣었다!

