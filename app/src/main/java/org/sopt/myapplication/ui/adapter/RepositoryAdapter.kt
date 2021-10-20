package org.sopt.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.myapplication.data.RepositoryData
import org.sopt.myapplication.databinding.ItemRepositoryBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    private val _repositoryData = mutableListOf<RepositoryData>()
    private var repositoryData : List<RepositoryData> = _repositoryData
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.RepositoryViewHolder, position: Int) {
        holder.onBind(repositoryData[position])
    }

    override fun getItemCount(): Int {
        return repositoryData.size
    }

    class RepositoryViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(repositoryData: RepositoryData){
            binding.apply {
                repository = repositoryData
                binding.executePendingBindings()
            }
        }
    }

    fun setRepository(repositoryData : List<RepositoryData>){
        this.repositoryData = repositoryData
        notifyDataSetChanged()
    }
}