package org.sopt.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.myapplication.data.FollowData
import org.sopt.myapplication.databinding.ItemFollowerBinding

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