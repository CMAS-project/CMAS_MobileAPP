package com.capstone.bangkit.cmas.module

import com.capstone.bangkit.cmas.data.remote.retrofit.ApiConfig
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiService

class ApiModule {

    fun provideApiService(): ApiService = ApiConfig.getApiService()
}