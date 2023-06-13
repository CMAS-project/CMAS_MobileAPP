package com.capstone.bangkit.cmas.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ScanResponse(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("value")
	val value: Double
) : Parcelable
