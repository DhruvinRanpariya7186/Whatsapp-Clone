package com.example.whatsapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.data.Chats
import com.example.whatsapp.databinding.ReciverChatLayoutBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ChatRecViewHolder(private val binding: ReciverChatLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    fun setData(chats : Chats){
        binding.tvRecMsg.text = chats.message
        val date = Date(chats.timeStamp.toLong())
        val formatter: DateFormat = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateFormatted: String = formatter.format(date)
        binding.tvRecTime.text = dateFormatted
    }
}
