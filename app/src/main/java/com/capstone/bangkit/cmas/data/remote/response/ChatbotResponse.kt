package com.capstone.bangkit.cmas.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatbotResponse(

	@field:SerializedName("response")
	val response: String
)
