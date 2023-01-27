package com.example.myartbookgalleryproject.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myartbookgalleryproject.R
import com.example.myartbookgalleryproject.adapter.ArtRecyclerAdapter
import com.example.myartbookgalleryproject.databinding.FragmentArtsBinding
import com.example.myartbookgalleryproject.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding? = null
    lateinit var viewModel : ArtViewModel

    //ItemTouchHelper class - This is a utility class to add swipe to dismiss and drag & drop support to RecyclerView.
    //Swipe left or right, it will be deleted
    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //we want to delete the chosen art from the room database
            //layoutposition is the position the user has chosen
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt) // deleted from room
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //initialising the viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        //binding the view to our layout
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding // no need to write findViewById now as we are using data binding
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)// this connects swipeCallback to recyclerView

        subscribeToObservers()

        //binding recyclerview to our adapter
        binding.recyclerViewArt.adapter = artRecyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener {
            //navigating  from ArtFragment (created class is ArtFragmentDirections) to ArtFragmentDetailFragment
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers()
    {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it // list of art is we get in it
        })
    }
    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}