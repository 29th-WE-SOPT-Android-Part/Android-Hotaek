package org.sopt.myapplication.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.sopt.myapplication.data.FollowData
import org.sopt.myapplication.databinding.ItemFollowerBinding
import org.sopt.myapplication.ui.DetailActivity
import java.util.*

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    private var followData = mutableListOf<FollowData>()


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

    fun setFollowData(followData: MutableList<FollowData>){
        this.followData = followData
        notifyDataSetChanged()
    }

    fun removeData(position: Int){
        followData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData(fromPos: Int, toPos: Int){
        Collections.swap(followData, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }
}