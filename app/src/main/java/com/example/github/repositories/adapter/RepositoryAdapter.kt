package com.example.github.repositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.data.LocalDataStore
import com.example.github.repositories.databinding.ItemBinding
import com.example.github.repositories.model.RepositoryDTO
import java.util.*

class RepositoryAdapter(
    val list: List<RepositoryDTO>,
    var onItemClick: (repository: RepositoryDTO) -> Unit
) :
    ListAdapter<RepositoryDTO, RepositoryAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBinding =
            (DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item,
                parent,
                false
            ))
        return ViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
            val item = list[adapterPosition]
            ("#" + (adapterPosition + 1) + ": " + item.full_name!!.uppercase(
                Locale.getDefault()
            )).also { binding.title.text = it }

            var des = (item?.description) ?: ""
            binding.description.text =
                if (des?.length!! > 150) des?.take(150)
                    .plus("...") else des
            binding.author.text = item?.owner?.login
            binding.image.setImageResource(
                if (LocalDataStore.instance.getBookmarks().contains(item))
                    R.drawable.baseline_bookmark_black_24
                else
                    R.drawable.baseline_bookmark_border_black_24
            )
            binding.newsContainer.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}