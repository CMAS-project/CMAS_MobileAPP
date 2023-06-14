package com.capstone.bangkit.cmas.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.bangkit.cmas.data.remote.response.ScanResponse
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel: ViewModel() {
    private val _scannedImage = MutableLiveData<File?>()
    val scannedImage: LiveData<File?>
        get() = _scannedImage

    fun setScannedImage(file: File?) {
        _scannedImage.value = file
    }

    private val _isBackCamera = MutableLiveData<Boolean>()
    val isBackCamera: LiveData<Boolean> = _isBackCamera

    fun setIsBackCamera(isBackCamera: Boolean) {
        _isBackCamera.value = isBackCamera
    }

    fun clearScannedImage() {
        _scannedImage.value = null
    }

    fun scanFace(apiService: ApiService, label: String, value: Double): LiveData<ScanResponse?> {
        val result = MutableLiveData<ScanResponse?>()

        val file = _scannedImage.value
        if (file != null) {
            val filePart = MultipartBody.Part.createFormData(
                "uploaded_file",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val labelPart = RequestBody.create("text/plain".toMediaTypeOrNull(), label)
            val valuePart = RequestBody.create("text/plain".toMediaTypeOrNull(), value.toString())

            apiService.scanFace(filePart, labelPart, valuePart).enqueue(object :
                Callback<ScanResponse> {
                override fun onResponse(call: Call<ScanResponse>, response: Response<ScanResponse>) {
                    if (response.isSuccessful) {
                        val scanResponse = response.body()
                        result.value = scanResponse
                    } else {
                        result.value = null
                    }
                }

                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                    result.value = null
                }
            })
        } else {
            result.value = null
        }

        return result
    }
}