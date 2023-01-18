package com.example.myartbookgalleryproject.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val total : Int,
    var totalHits: Int
)
