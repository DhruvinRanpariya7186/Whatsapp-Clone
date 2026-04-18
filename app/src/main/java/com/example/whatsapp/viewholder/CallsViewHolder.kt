package com.example.whatsapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.R
import com.example.whatsapp.data.CallsModel
import com.example.whatsapp.databinding.ItemCallLayoutBinding

class CallsViewHolder(private val binding: ItemCallLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(model: CallsModel) {
        binding.callUserImage.setImageResource(R.drawable.john)
        binding.callUserName.text = model.name
    }
}
