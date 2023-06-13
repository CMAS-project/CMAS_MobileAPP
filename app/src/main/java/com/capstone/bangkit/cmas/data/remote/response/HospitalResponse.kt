package com.capstone.bangkit.cmas.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HospitalResponse(

	@field:SerializedName("hospitals")
	val hospitals: List<HospitalsItem>
)

@Parcelize
data class HospitalsItem(

	@field:SerializedName("latitude")
	val latitude: Double?,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("longitude")
	val longitude: Double?
) : Parcelable
