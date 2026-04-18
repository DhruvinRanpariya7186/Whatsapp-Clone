package com.example.whatsapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.RecyclerViewItemClicked
import com.example.whatsapp.data.Users
import com.example.whatsapp.databinding.RecyclerChatsLayoutBinding

class ChatsViewHolder(
    private val binding: RecyclerChatsLayoutBinding,
    private val recyclerViewItemClicked: RecyclerViewItemClicked
) : RecyclerView.ViewHolder(binding.root){

    fun setData(users: Users){
        binding.tvUserName.text = users.profileName
        binding.llUsers.setOnClickListener {
            recyclerViewItemClicked.onItemClicked(adapterPosition, users)
        }
    }
}
