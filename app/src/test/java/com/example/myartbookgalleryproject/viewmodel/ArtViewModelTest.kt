package com.example.myartbookgalleryproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainCoroutineRule
import com.example.myartbookgalleryproject.respository.FakeArtRepository
import com.example.myartbookgalleryproject.util.Status
import com.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi // this is needed when are adding rules
class ArtViewModelTest {

    // This rules means run all the background threads or task in main thread only.
    // Since we are not running in Android Test, then we don't need rule
    // We don't want any threading basically

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup()
    {
        //FakeRepository
        //Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`()
    {
        viewModel.makeArt("iron man", "ruhul", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() // this is livedata // we will convert livedata to normal data format
        assertThat(value.status).isEqualTo(Status.ERROR)// assertThat comes from truth library
    }
    @Test
    fun `insert art without name returns error`()
    {
        viewModel.makeArt("", "ruhul", "2001")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() // this is livedata // we will convert livedata to normal data format
        assertThat(value.status).isEqualTo(Status.ERROR)// assertThat comes from truth library

    }
    @Test
    fun `insert art without artistName returns error`()
    {
        viewModel.makeArt("iron man", "", "2001")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest() // this is livedata // we will convert livedata to normal data format
        assertThat(value.status).isEqualTo(Status.ERROR)// assertThat comes from truth library

    }
}