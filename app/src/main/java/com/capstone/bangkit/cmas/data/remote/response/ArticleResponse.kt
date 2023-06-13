package com.capstone.bangkit.cmas.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArticleResponse(

	@SerializedName("ArticleResponse")
	val articleResponse: List<ArticleResponseItem>
)

@Parcelize
data class ArticleResponseItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
): Parcelable
