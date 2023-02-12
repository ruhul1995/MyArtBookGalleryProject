package com.example.myartbookgalleryproject.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myartbookgalleryproject.model.ImageResponse
import com.example.myartbookgalleryproject.repository.ArtRepositoryInterface
import com.example.myartbookgalleryproject.roomdb.Art
import com.example.myartbookgalleryproject.util.Resource

class FakeArtRepository: ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData()
    {
        artsLiveData.postValue(arts)
    }
}