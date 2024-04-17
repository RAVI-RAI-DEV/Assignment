package com.example.acharyaprashant.model.unsplash

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    val id: String,
    @SerializedName("urls") val urls: UnsplashPhotoUrls
)