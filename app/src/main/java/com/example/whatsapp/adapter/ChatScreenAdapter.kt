package com.example.whatsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.data.Chats
import com.example.whatsapp.databinding.ReciverChatLayoutBinding
import com.example.whatsapp.databinding.SenderChatLayoutBinding
import com.example.whatsapp.viewholder.ChatRecViewHolder
import com.example.whatsapp.viewholder.ChatSendViewHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatScreenAdapter(private var chatsList: List<Chats>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_TYPE_SENT = 1
    private val ITEM_TYPE_RECEIVED = 2
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_SENT -> {
                val binding = SenderChatLayoutBinding.inflate(layoutInflater, parent, false)
                ChatSendViewHolder(binding)
            }
            ITEM_TYPE_RECEIVED -> {
                val binding = ReciverChatLayoutBinding.inflate(layoutInflater, parent, false)
                ChatRecViewHolder(binding)
            }
            else -> throw IllegalStateException("No matching views found")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chats = chatsList[position]
        if (holder is ChatSendViewHolder){
            holder.setData(chats)
        }else if (holder is ChatRecViewHolder){
            holder.setData(chats)
        }
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatsList[position].sender == Firebase.auth.currentUser?.uid){
            ITEM_TYPE_SENT
        } else{
            ITEM_TYPE_RECEIVED
        }
    }
    
    fun updateList(chatsList: List<Chats>){
        this.chatsList = chatsList
        notifyDataSetChanged()
    }
}
