package com.example.myartbookgalleryproject.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.myartbookgalleryproject.adapter.ArtRecyclerAdapter
import com.example.myartbookgalleryproject.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide: RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        // Instantiating all the fragments here
        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}