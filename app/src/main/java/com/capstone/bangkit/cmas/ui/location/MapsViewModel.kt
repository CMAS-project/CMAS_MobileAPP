package com.capstone.bangkit.cmas.ui.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.bangkit.cmas.data.remote.response.HospitalResponse
import com.capstone.bangkit.cmas.data.remote.response.HospitalsItem
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {
    private val _nearbyHospitals = MutableLiveData<List<HospitalsItem>>()
    val nearbyHospitals: LiveData<List<HospitalsItem>> = _nearbyHospitals

    fun getNearbyHospitals(latitude: Double, longitude: Double) {
        val client = ApiConfig.getApiService()

        val call = client.getNearbyHospitals(latitude, longitude)
        call.enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(
                call: Call<HospitalResponse>,
                response: Response<HospitalResponse>
            ) {
                if (response.isSuccessful) {
                    val hospitalResponse = response.body()
                    _nearbyHospitals.value = hospitalResponse?.hospitals
                } else {
                    // Tangani respons yang tidak berhasil
                }
            }

            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                // Tangani kegagalan jaringan
                Log.e(MapsViewModel.TAG, "Failure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private var TAG = "MapsViewModel"
    }
}