package com.example.myartbookgalleryproject.api

import com.example.myartbookgalleryproject.model.ImageResponse
import com.example.myartbookgalleryproject.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String, // this searchQuery parameter is nothing but ? in api url i.e " https://pixabay.com/api/?key = ..."
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponse>
}