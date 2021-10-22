# 영상

![Hnet-image (5)](https://user-images.githubusercontent.com/71322949/138452978-0966e3d9-b0ad-4d1d-80f3-8ab5e6994926.gif)



![Hnet-image (6)](https://user-images.githubusercontent.com/71322949/138453369-ffed429e-9f53-424c-8b22-2e6bcadd2ce7.gif)



# 코드 설명

### LEVEL_1-1

## 1. HomeActivity.kt

```kotlin
 private fun initHome(){
        binding.user = HomeData("곽호택","25","INFP","만나서 반가워요 안녕하세요!!!",
        R.drawable.hotaek)

        changeFragment(R.id.fragment_container_recycler, FollowerFragment())
    }

    private fun goFragment(){
        with(binding){
            btnFollower.setOnClickListener {
                changeFragment(R.id.fragment_container_recycler, FollowerFragment())
            }

            btnRepository.setOnClickListener {
                changeFragment(R.id.fragment_container_recycler, RepositoryFragment())
            }
        }

    }
```

```Kotlin
fun AppCompatActivity.changeFragment(layoutRes: Int, fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(layoutRes, fragment)
        .addToBackStack(null)
        .commit()
}
```

따로 changeFragment라는 함수를 만들어서 Fragment 전환이 이루어지도록 작성하였습니다.

초기 default Fragment는 FollowerFragment()로 설정하였고, 버튼 클릭시에 RepositoryFragment()로 전환하도록 작성했습니다.



## 2. FollowerAdapter

```kotlin
class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private val _followData = mutableListOf<FollowData>()
    private var followData : List<FollowData> = _followData


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowAdapter.FollowViewHolder {
        val binding = ItemFollowerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowAdapter.FollowViewHolder, position: Int) {
        holder.onBind(followData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userImage", followData[position].image)
            intent.putExtra("userName", followData[position].name)
            intent.putExtra("userIntroduce", followData[position].introduce)
            startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int {
        return followData.size
    }

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

    fun setFollowData(followData: List<FollowData>){
        this.followData = followData
        notifyDataSetChanged()
    }
}
```

FollowData라는 데이터 클래스를 만든 뒤 위와 같이 RecyclerView를 작성하였습니다.

FollowRecyclerView는 LinearLayoutManager으로 RepositoryRecyclerView의 경우는 GridLayoutManager으로 만들었습니다.

## fragment_follower.xml

```Kotlin
  <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_follow"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginStart="10dp"
            android:layout_marginTop="20dp"/>
```

##  LEVEL_1-2 

## fragment_repository.xml

```kotl
  <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_repository"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
            android:orientation="vertical"
            app:spanCount="2"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginStart="10dp"
            android:layout_marginTop="20dp"/>
```





## 3. FollowFragment.kt

```kotlin
private fun initFollow(){
    followAdapter = FollowAdapter()
    binding.recyclerFollow.adapter = followAdapter
    binding.recyclerFollow.addItemDecoration(MyDecoration(50, Color.BLUE))
    followAdapter.setFollowData(
        listOf(
            FollowData(R.drawable.hotaek, "곽호택", "안드로이드 OB"),
            FollowData(R.drawable.hotaeks, "곽호택", "차로 갓갓갓갓"),
            FollowData(R.drawable.hotaekes, "곽호택", "솝트 갓갓갓갓")
        )
    )


}
```



### LEVEL_2-1

## 1. FollowAdapter.kt

```kotl
 override fun onBindViewHolder(holder: FollowAdapter.FollowViewHolder, position: Int) {
        holder.onBind(followData[position])
        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userImage", followData[position].image)
            intent.putExtra("userName", followData[position].name)
            intent.putExtra("userIntroduce", followData[position].introduce)
            startActivity(holder.itemView.context, intent, null)

        }
    }
```

FollowAdapter의 onBindViewHolder안에 클릭시 아이템의 position에 따라 intent로 데이터를 전달해주는 방식을 사용하였습니다!!



## 2. DetailActivity.kt

```k
 private fun initDetail() {
        val image = intent.getIntExtra("userImage", 0)
        val name = intent.getStringExtra("userName")
        val introduce = intent.getStringExtra("userIntroduce")
        with(binding) {
            imgDetailProfile.setImageResource(image)
            textDetailName.text = name
            textProfileInputIntroduce.text = introduce
        }
    }
```



## LEVEL_2_2

## 1. MyDecoration.kt

```ko
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

커스텀으로 만들기 위해 MyDecoration이라는 클래스를 만들었습니다. 이때 RecyclerView의 ItemDecoration()을 상속받았고, 구분선의 높이와 색상을 설정해줬습니다!

- onDraw의 경우 아이템이 그려지기 전에 호출되는 함수로 아이템 때문에 구분선이 가려져서 보일 수 있습니다.

- onDrawOver의 경우에는 onDraw와 반대로 아이템이 그려진 후에 호출되서 아이템 위에 위치해서 보입니다

- getItemOffsets의 경우는 구분선의 크기를 지정하는 함수로 따로 지정해주지 않으면 크기가 없는 rect를 반환한다고 한다.

  여기서 rect란! 사각형을 만드는 클래스라고 생각하면 된다. 이때 아래 그림과 같이 4개의 점들이 모여서 하나의 사각형을 이루는 구조를 가진다.

![img](https://t1.daumcdn.net/cfile/tistory/0277DD385105544E21)

```kotl
  binding.recyclerFollow.addItemDecoration(MyDecoration(50, Color.BLUE))
```

MyDecoration 클래스를 만들어준 뒤 이를 recyclerView의 addItemDecoration함수를 통해 넣어준다.



### LEVEL_2-3

## 1. FollowFragment.kt

```kotl
  private fun itemTouch(){
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos: Int = viewHolder.adapterPosition
                val toPos: Int = target.adapterPosition
                followAdapter.swapData(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                followAdapter.removeData(viewHolder.layoutPosition)
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerFollow)
    }
```



## 2. FollowAdapter.kt

```kot
 fun removeData(position: Int){
        followData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData(fromPos: Int, toPos: Int){
        Collections.swap(followData, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }
```





## LEVEL_3-1

- 보일러플레이트 : 최소한의 변경으로 여러 곳에서 사용되고, 반복적으로 비슷한 형태를 띄는 코드!
- 매번 작성해줘야 하는 binding!! 이를 개선하기 위해 추상 클래스로 BaseActivity를 만들어줬다 Fragment도 함께!

## 1.BaseActivity.kt 

```Kotl
abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
```

```Kotl
abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutRes: Int): Fragment() {
    private var _binding : T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```





## LEVEL_3-2

diffUtil 사용해 볼 예정(과제X)







# 이번 과제를 통해 배운 내용

ItemDecorator라는 것을 이번 과제에서  처음 배웠는데 매번 구분선 그려주려고 View로 그렸는데... 얼른 빨리 고쳐야겠다!! 다만 custom하기가 조금 어려운 것 같아서 조금 더 공부할 필요성을 느꼈다.

그리고 drag and drop과 Swipe의 경우에도 사용할 일이 없어서 처음 써봤는데, 나중에 과제에서 썼던 코드와 동일하게 작성하면 될 것 같다!