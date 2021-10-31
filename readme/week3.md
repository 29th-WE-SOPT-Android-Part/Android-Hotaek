# 영상

![Android-Emulator-Pixel_3_XL_API_28_5554-2021-10-31-14-44-44](https://user-images.githubusercontent.com/71322949/139569881-9fd46638-d483-4d37-94df-f50e3c32c3a5.gif)

![Android-Emulator-Pixel_3_XL_API_28_5554-2021-10-31-14-45-04](https://user-images.githubusercontent.com/71322949/139569893-86063465-61c1-42c3-830c-2edcab28619c.gif)





# 코드 설명

### LEVEL 1 필수과제

## 1. selector_rectangle.xml

```Kotlin
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:state_focused="true"
        android:drawable="@drawable/rectangle_focus"/>
    <item
        android:state_focused="false"
        android:drawable="@drawable/rectangle_no_focus"/>
</selector>
```

위 코드와 같이 drawable 폴더 안에 selector_rectangle.xml을 selector로 지정하여 만들어 줬고 아래 EditText의 background에 넣어줬다.



## 1-2. activity_signin.xml

```Kotlin
 <EditText
            android:id="@+id/et_id"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:background="@drawable/selector_rectangle"
            android:hint="@string/input_id"
            android:includeFontPadding="false"
            style="@style/SectionH1"
            app:layout_constraintTop_toBottomOf="@+id/text_id"
            app:layout_constraintStart_toStartOf="@id/text_id"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginEnd="50dp"
            android:layout_marginTop="6dp"
            android:padding="12dp" />
```



## 1-3.activity_sign_up.xml

```Kotlin
  <EditText
            android:id="@+id/et_signup_name"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:background="@drawable/selector_rectangle"
            android:hint="@string/input_name"
            style="@style/SectionH1"
            android:includeFontPadding="false"
            app:layout_constraintTop_toBottomOf="@+id/text_signup_name"
            app:layout_constraintStart_toStartOf="@id/text_signup_name"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginEnd="20dp"
            android:layout_marginTop="6dp"
            android:padding="12dp" />
```





## LEVEL2 도전 과제



### 2 - 1 ViewPager2 중첩 스크롤 문제

### NestedScrollableHost.kt

```Kotlin
class NestedScrollableHost : ConstraintLayout {
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

        // Early return if child can't scroll in same direction as parent
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

            // assuming ViewPager2 touch-slop is 2x touch-slop of child
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    // Gesture is perpendicular, allow all parents to intercept
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    // Gesture is parallel, query child if movement in that direction is possible
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        // Child can scroll, disallow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        // Child cannot scroll, allow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}
```

방향이 동일한 ViewPager2 객체 내의 스크롤 뷰를 지원하기 위해 requestDisallowInterceptTouchEvent()를 호출해야 한다.고 구글 공식 문서에 나와있었다. 그 뒤 이를 xml에서 호출하면 된다.

### activity_home.xml

```Kotlin
 <org.sopt.myapplication.util.NestedScrollableHost
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintBottom_toTopOf="@+id/bnv_home"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

          ...

        </org.sopt.myapplication.util.NestedScrollableHost>
```



### 2 - 2 다른 이미지 넣기

### BindingAdapter.kt

```Kotlin
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

Data Binding을 사용하므로 Glide 라이브러리를 활용해서 이미지를 넣는 BIndingAdapter를 만들었다.

circleCrop() 메소드를 통해서 이미지를 원모양으로 만들어줬다.

### item_follower.xml

```Kotlin
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="follow"
            type="org.sopt.myapplication.data.FollowData" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <ImageView
                android:id="@+id/image_profile"
                imageBind="@{follow.image}"
                android:layout_width="100dp"
                android:layout_height="100dp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <TextView
                android:id="@+id/text_name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{follow.name}"
                android:textStyle="bold"
                app:layout_constraintStart_toEndOf="@+id/image_profile"
                app:layout_constraintTop_toTopOf="@+id/image_profile" />

            <TextView
                android:id="@+id/text_follow_introduce"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="@{follow.introduce}"
                android:maxLines="2"
                android:ellipsize="end"
                app:layout_constraintStart_toStartOf="@+id/text_name"
                app:layout_constraintTop_toBottomOf="@+id/text_name" />

        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

그 뒤 ImageView에 만들어 놓은 imageBind로 FollowData라는 데이터 클래스로 이미지 Url을 연결시켜줬다.

### FollowerFragment.kt

```Kotlin
 private fun initFollow(){
        followAdapter = FollowAdapter()
        binding.recyclerFollow.adapter = followAdapter
        binding.recyclerFollow.addItemDecoration(MyDecoration(50, Color.BLUE))
        followAdapter.setFollowData(
            mutableListOf(
                FollowData(R.drawable.hotaek, "곽호택", "안드로이드 OB"),
                FollowData(R.drawable.hotaeks, "곽호택", "차로 갓갓갓갓"),
                FollowData(R.drawable.hotaekes, "곽호택", "솝트 갓갓갓갓")
            )
        )
    }
```

그 뒤에 Adapter에 3가지 다른 이미지 데이터를 연결 시켜줬다.





## LEVEL-3 심화과제

### 3 - 1 DataBinding

### item_follow.xml

```Kotllin
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="follow"
            type="org.sopt.myapplication.data.FollowData" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginTop="10dp"
            android:layout_marginBottom="10dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <ImageView
                android:id="@+id/image_profile"
                imageBind="@{follow.image}"
                android:layout_width="100dp"
                android:layout_height="100dp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <TextView
                android:id="@+id/text_name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@{follow.name}"
                android:textStyle="bold"
                app:layout_constraintStart_toEndOf="@+id/image_profile"
                app:layout_constraintTop_toTopOf="@+id/image_profile" />

            <TextView
                android:id="@+id/text_follow_introduce"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="@{follow.introduce}"
                android:maxLines="2"
                android:ellipsize="end"
                app:layout_constraintStart_toStartOf="@+id/text_name"
                app:layout_constraintTop_toBottomOf="@+id/text_name" />

        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

databinding을 활용하여 FollwData와 각 ImageView 또는 TextView를 연결 시켜줬다.



```Kotlin
 class FollowViewHolder(
        val binding: ItemFollowerBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(followData: FollowData){
            binding.apply{
                follow = followData
                binding.executePendingBindings()
            }

        }
    }
```

그 뒤 FollowAdapter의 ViewHolder 부분에서 onBind 함수를 만들어 줄때, databinding으로 이름을 지정한 binding.follow에 follwData클래스를 연결시켜주는 코드를 작성했다. 이렇게 작성할 경우 데이터가 기존에 xml에 작성해놓은 뷰에 자동으로 들어가서 코드를 줄일 수 있다.



# 배운내용



ViewPager2에서의 중첩 스크롤 문제에 대해 경험해본적이 없어서, 알 수 없었는데 이번 과제를 계기로 겹치는 상황에서 스크롤을 위해 requestDisallowInterceptTouchEvent()를 호출하는 클래스를 만들어야 함을 알게 됐다. 아직 그 안에 class가 어떻게 작동하는지 정확하게 모르지만 자세히 공부해볼 계획이다!
