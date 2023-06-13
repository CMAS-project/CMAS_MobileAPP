package com.capstone.bangkit.cmas.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.bangkit.cmas.data.remote.response.ArticleResponse
import com.capstone.bangkit.cmas.data.remote.response.ArticleResponseItem
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    private val _listArticle = MutableLiveData<List<ArticleResponseItem>>()
    val listArticle: LiveData<List<ArticleResponseItem>> = _listArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getArticle(title: String, description: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getArticle(title, description)
        client.enqueue(object : Callback<ArticleResponse> {

            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listArticle.value = response.body()?.articleResponse
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticleViewModel"
    }
}