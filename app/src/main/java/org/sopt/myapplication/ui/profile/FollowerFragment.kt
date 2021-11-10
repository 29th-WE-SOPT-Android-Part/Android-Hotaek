package org.sopt.myapplication.ui.profile


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.sopt.myapplication.R
import org.sopt.myapplication.data.FollowData
import org.sopt.myapplication.databinding.FragmentFollowerBinding
import org.sopt.myapplication.ui.adapter.FollowAdapter
import org.sopt.myapplication.ui.base.BaseFragment
import org.sopt.myapplication.util.MyDecoration


class FollowerFragment : BaseFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {
    private lateinit var followAdapter: FollowAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFollow()
        itemTouch()
    }


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
}