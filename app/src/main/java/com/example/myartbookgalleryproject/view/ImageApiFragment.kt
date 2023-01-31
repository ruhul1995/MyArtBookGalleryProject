package com.example.myartbookgalleryproject.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.myartbookgalleryproject.R
import com.example.myartbookgalleryproject.adapter.ImageRecyclerAdapter
import com.example.myartbookgalleryproject.databinding.FragmentImageApiBinding
import com.example.myartbookgalleryproject.util.Status
import com.example.myartbookgalleryproject.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentImageApiBinding ? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job : Job? = null

        //making a call with a 1 sec delay to make a call for next character in editable text
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty())
                    {
                        viewModel.searchForImage(it.toString()) // it.toString() is the string to be searched
                    }
                }
            }
        }

        //Observing in ArtViewModel
        subscribeToObserver()


        //setting adapter for ImageApiFragment
        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack() // to go back to previous fragment i.e ArtDetailsFragment
            viewModel.setSelectedImage(it)
        }
    }

    fun subscribeToObserver()
    {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status)
            {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {  imageResult ->
                        imageResult.previewURL
                    }
                    //to get the url
                    imageRecyclerAdapter.images = urls ?: listOf() // it is null, we give empty list
                    fragmentBinding?.progressBar?.visibility = View.GONE // not showing in success case
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE // loading we are showng
                }
            }
        })
    }
}