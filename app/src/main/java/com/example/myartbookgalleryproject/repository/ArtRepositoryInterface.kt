package com.example.myartbookgalleryproject.repository

import androidx.lifecycle.LiveData
import com.example.myartbookgalleryproject.model.ImageResponse
import com.example.myartbookgalleryproject.roomdb.Art
import com.example.myartbookgalleryproject.util.Resource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn


interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}