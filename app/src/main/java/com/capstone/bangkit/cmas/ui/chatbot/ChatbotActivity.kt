package com.capstone.bangkit.cmas.ui.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityChatbotBinding

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customToolbar()
    }

    private fun customToolbar() {
        binding.apply {
            toolbar.navBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}