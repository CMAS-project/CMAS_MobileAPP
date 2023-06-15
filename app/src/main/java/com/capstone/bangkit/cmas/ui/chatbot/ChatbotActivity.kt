package com.capstone.bangkit.cmas.ui.chatbot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.bangkit.cmas.adapter.MessageAdapter
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiConfig
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiService
import com.capstone.bangkit.cmas.databinding.ActivityChatbotBinding
import com.capstone.bangkit.cmas.model.ChatbotViewModelFactory
import com.capstone.bangkit.cmas.utils.ChatMessage

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatAdapter: MessageAdapter
    private val chatbotViewModel: ChatbotViewModel by viewModels{
        ChatbotViewModelFactory(apiService)
    }
    private val apiService: ApiService = ApiConfig.getApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customToolbar()
        setupRecyclerView()
        setupChatbot()
        btnSend()
    }

    private fun setupRecyclerView() {
        chatAdapter = MessageAdapter()
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            adapter = chatAdapter
        }
    }

    private fun setupChatbot() {
        chatbotViewModel.response.observe(this) { response ->
            response?.let {
                val botMessage = ChatMessage(it.response, ChatMessage.Type.BOT)
                chatAdapter.addMessage(botMessage)
                binding.rvChat.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage(message: String) {
        val userMessage = ChatMessage(message, ChatMessage.Type.USER)
        chatAdapter.addMessage(userMessage)
        binding.rvChat.smoothScrollToPosition(chatAdapter.itemCount - 1)

        chatbotViewModel.sendMessage(message, chatAdapter)
    }

    private fun btnSend() {
        binding.btnSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            if (message.isNotBlank()) {
                sendMessage(message)
                binding.edtMessage.text.clear()
            } else {
                Toast.makeText(this, "Ketik pesanmu dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun customToolbar() {
        binding.apply {
            toolbar.navBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}