package com.example.myartbookgalleryproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myartbookgalleryproject.view.ArtFragmentFactory
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint // hilt knows now to start from here
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory // fragment manager should know about artfragmentfactory

        setContentView(R.layout.activity_main)

    }
}