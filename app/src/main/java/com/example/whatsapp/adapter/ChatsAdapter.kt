package com.example.whatsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.RecyclerViewItemClicked
import com.example.whatsapp.data.Users
import com.example.whatsapp.databinding.RecyclerChatsLayoutBinding
import com.example.whatsapp.viewholder.ChatsViewHolder

class ChatsAdapter(
    private var usersList: List<Users>,
    private val recyclerViewItemClicked: RecyclerViewItemClicked
) : RecyclerView.Adapter<ChatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val binding = RecyclerChatsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatsViewHolder(binding, recyclerViewItemClicked)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val users = usersList[position]
        holder.setData(users)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun updateData(newList: List<Users>) {
        usersList = newList
        notifyDataSetChanged()
    }
}
