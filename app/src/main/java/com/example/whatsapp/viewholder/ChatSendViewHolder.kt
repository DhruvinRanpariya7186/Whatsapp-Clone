package com.example.whatsapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.data.Chats
import com.example.whatsapp.databinding.SenderChatLayoutBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ChatSendViewHolder(private val binding: SenderChatLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setData(chats: Chats) {
        binding.tvChatMessage.text = chats.message
        val date = Date(chats.timeStamp.toLong())
        val formatter: DateFormat = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateFormatted: String = formatter.format(date)
        binding.tvTime.text = dateFormatted
    }
}
