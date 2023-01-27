package com.example.myartbookgalleryproject.repository

import androidx.lifecycle.LiveData
import com.example.myartbookgalleryproject.api.RetrofitAPI
import com.example.myartbookgalleryproject.model.ImageResponse
import com.example.myartbookgalleryproject.roomdb.Art
import com.example.myartbookgalleryproject.roomdb.ArtDao
import com.example.myartbookgalleryproject.util.Resource
import dagger.hilt.InstallIn
import javax.inject.Inject

//We are overriding the methods from ArtRepositoryInterface
//We will using dao to perform the operation
//Here we will inject dao using hilt support

class ArtRepository @Inject constructor (
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
    ): ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArt()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {

            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful)
            {
                response.body()?.let {
                    return  Resource.success(it) // it will have image response in it
                } ?: Resource.error("Error", null)
            }
            else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}