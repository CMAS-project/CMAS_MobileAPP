package com.capstone.bangkit.cmas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.cmas.data.remote.response.ChatbotResponse
import com.capstone.bangkit.cmas.databinding.MessageItemBinding
import com.capstone.bangkit.cmas.utils.ChatMessage

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessagingViewHolder>() {

    private val messages : MutableList<ChatMessage> = mutableListOf()

    inner class MessagingViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chatMessage: ChatMessage) {
            if (chatMessage.type == ChatMessage.Type.USER) {
                binding.tvBotMessage.visibility = View.GONE
                binding.tvMessage.visibility = View.VISIBLE
                binding.tvMessage.text = chatMessage.message
            } else {
                binding.tvMessage.visibility = View.GONE
                binding.tvBotMessage.visibility = View.VISIBLE
                binding.tvBotMessage.text = chatMessage.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageItemBinding.inflate(inflater, parent, false)
        return MessagingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessagingViewHolder, position: Int) {
        val chatMessage = messages[position]
        holder.bind(chatMessage)
    }


    fun addMessage(chatMessage: ChatMessage) {
        // Menghapus pesan BOT sebelum menambahkan pesan baru
        val lastMessageType = if (messages.isNotEmpty()) messages.last().type else null
        if (lastMessageType != ChatMessage.Type.BOT && chatMessage.type == ChatMessage.Type.BOT) {
            messages.add(chatMessage)
            notifyItemInserted(messages.size - 1)
        }
        else if (lastMessageType == ChatMessage.Type.BOT && chatMessage.type == ChatMessage.Type.BOT) {
            // Jika pesan sebelumnya juga dari BOT, ganti pesan tersebut dengan yang baru
            messages[messages.lastIndex] = chatMessage
            notifyItemChanged(messages.lastIndex)
        }
        else {
            messages.add(chatMessage)
            notifyItemInserted(messages.size - 1)
        }
    }


}