package com.example.myartbookgalleryproject.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.myartbookgalleryproject.R
import com.example.myartbookgalleryproject.databinding.FragmentArtDetailsBinding
import com.example.myartbookgalleryproject.util.Status
import com.example.myartbookgalleryproject.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment  @Inject constructor(
    private val glide: RequestManager
): Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBinding: FragmentArtDetailsBinding? = null // type here that is fragmentArtDetailsBinding is created i.e Fragment + classname(minus fragment termfrom class) + binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.artImgView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        //onBack Press
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack() // their will be only  artFragment
            }
        }
        //calling the callback for back button press using onBackPressedDispatcher
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        //if it is success, then it is added in room database
        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.nameEditText.text.toString(),
                binding.artDetailArtistNametxtV.text.toString(),
                binding.artDetailYearTxtV.text.toString()) // checks if everything is okay like data is okay
        }
    }

    private fun subscribeToObservers()
    {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImgView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            //give resources of art
            //checking for data entered by user

            when(it.status)
            {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg() //
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}