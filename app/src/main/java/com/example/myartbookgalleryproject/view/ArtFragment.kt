package com.example.myartbookgalleryproject.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myartbookgalleryproject.R
import com.example.myartbookgalleryproject.databinding.FragmentArtsBinding

class ArtFragment : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding the view to our layout
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding // no need to write findViewById now as we are using data binding

        binding.fab.setOnClickListener {
            //navigating  from ArtFragment (created class is ArtFragmentDirections) to ArtFragmentDetailFragment
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}