package com.example.myartbookgalleryproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myartbookgalleryproject.model.ImageResponse
import com.example.myartbookgalleryproject.repository.ArtRepositoryInterface
import com.example.myartbookgalleryproject.roomdb.Art
import com.example.myartbookgalleryproject.util.Resource
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//Note - we can have multiple viewmodel to represent view, since we have less no of operations, hence using the same viewmodel here

// class ArtViewModel @ViewModelInject constructor { } .....
// here @ViewModelInject in class definition is deprecated.
// For that we use @Inject in class name and @HiltViewModel tag for class. i.e as shown below

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    // Art Fragment
    val artList = repository.getArt()

    //Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>> // this is exposed to the View
    get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    // Art Details Fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    // resetting
    fun resetInsertArtMsg()
    {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String)
    {
        selectedImage.postValue(url)
    }

    // Since deleteArt is suspend function, hence we are calling from coroutine using viewModelScope
    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }
    //Since insert is suspend function also,  hence we are calling from coroutine using viewModelScope
    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    // Text fields cannot be null | Checking for art details cannot be null
    fun makeArt(name: String, artistName: String, year: String)
    {
        if ( name.isEmpty() || artistName.isEmpty() || year.isEmpty())
        {
            insertArtMsg.postValue(Resource.error("Enter name, artist, year ", null))
        }

        val yearInt = try {
            year.toInt()
        }
        catch (e: Exception)
        {
            insertArtMsg.postValue(Resource.error("Year should be number", null))
        }

        val art = Art(name, artistName, yearInt as Int, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }


    fun searchForImage(searchString: String)
    {
        if (searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        //once we get the image back
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response // postValue method can also be used here
        }
    }

}