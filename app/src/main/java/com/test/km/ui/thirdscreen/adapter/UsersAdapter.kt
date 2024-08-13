package com.test.km.ui.thirdscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.km.R
import com.test.km.data.response.DataItem
import com.test.km.databinding.ItemUserBinding

class UsersAdapter(private val onItemClick: (DataItem) -> Unit):
    PagingDataAdapter<DataItem, UsersAdapter.ListViewHolder>(UsersDiffCallback()) {

    override fun onBindViewHolder(holder: UsersAdapter.ListViewHolder, position: Int) {
        val listUsers = getItem(position)
        if (listUsers != null) {
            holder.bind(listUsers)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    inner class ListViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(userImage)
                val firstName = user.firstName
                val lastName = user.lastName
                tvEmail.text = user.email
                tvUsername.text = itemView.context.getString(R.string.firstLastName,firstName,lastName)

                // Set click listener
                itemView.setOnClickListener {
                    onItemClick(user)
                }
            }
        }
    }

    private class UsersDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

}