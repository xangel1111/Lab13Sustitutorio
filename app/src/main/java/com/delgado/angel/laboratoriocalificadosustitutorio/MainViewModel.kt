package com.delgado.angel.laboratoriocalificadosustitutorio

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel {

    val postList = MutableLiveData<List<PostResponse>>()

    private val isLoading = MutableLiveData<Boolean>()

    val errorApi = MutableLiveData<String>()

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(PostApi::class.java).getPosts()
                if (call.isSuccessful) {
                    val response = call.body()?.toString()
                    // Log.d("MainViewModel", "Response: $response")
                    call.body()?.let { posts ->
                        // Log.d("MainViewModel", "Posts received: ${posts.size}")
                        postList.postValue(posts)
                        // Log.d("MainViewModel", "First post: ${posts[0]}")
                    }
                } else {
                    errorApi.postValue("Error de API: ${call.code()}")
                }
                isLoading.postValue(false)
            } catch (e: Exception) {
                errorApi.postValue(e.message)
                isLoading.postValue(false)
                Log.e("MainViewModel", "Error: ${e.message}")
            }
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}