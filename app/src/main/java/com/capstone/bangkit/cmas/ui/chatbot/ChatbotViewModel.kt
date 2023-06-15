package com.capstone.bangkit.cmas.ui.chatbot

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.bangkit.cmas.adapter.MessageAdapter
import com.capstone.bangkit.cmas.data.remote.response.ChatbotResponse
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiService
import com.capstone.bangkit.cmas.model.ChatbotRequest
import com.capstone.bangkit.cmas.utils.ChatMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatbotViewModel(private val apiService: ApiService): ViewModel() {

    private val _response = MutableLiveData<ChatbotResponse>()
    val response: LiveData<ChatbotResponse> = _response

    fun sendMessage(message: String, chatbotAdapter: MessageAdapter) {
        val call = apiService.chatbot(ChatbotRequest(message))

        call.enqueue(object : Callback<ChatbotResponse> {
            override fun onResponse(
                call: Call<ChatbotResponse>,
                response: Response<ChatbotResponse>
            ) {
                if (response.isSuccessful) {
                    val chatbotResponse = response.body()
                    chatbotResponse?.let {
                        _response.value = it
                        val botMessage = ChatMessage(it.response, ChatMessage.Type.BOT)
                        chatbotAdapter.addMessage(botMessage)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ChatbotResponse>, t: Throwable) {
//                _response.value = "Gagal terkoneksi dengan EmoWell"
            }

        })
    }
}