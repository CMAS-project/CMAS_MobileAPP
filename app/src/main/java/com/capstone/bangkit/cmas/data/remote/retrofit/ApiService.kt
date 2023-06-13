package com.capstone.bangkit.cmas.data.remote.retrofit

import com.capstone.bangkit.cmas.data.remote.response.ArticleResponse
import com.capstone.bangkit.cmas.data.remote.response.HospitalResponse
import com.capstone.bangkit.cmas.data.remote.response.ScanResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("news/mental-health/")
    fun getArticle(
        @Query("title") title: String,
        @Query("description") description: String
    ): Call<ArticleResponse>

    @GET("nearby_hospitals")
    fun getNearbyHospitals(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<HospitalResponse>

    @Multipart
    @POST("predict_image")
    fun scanFace (
        @Part file: MultipartBody.Part,
        @Part("label") label: RequestBody,
        @Part("value") value: RequestBody
    ): Call<ScanResponse>

}