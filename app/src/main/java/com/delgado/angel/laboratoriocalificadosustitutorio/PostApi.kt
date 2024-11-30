package com.delgado.angel.laboratoriocalificadosustitutorio

import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): Response<List<PostResponse>>

}