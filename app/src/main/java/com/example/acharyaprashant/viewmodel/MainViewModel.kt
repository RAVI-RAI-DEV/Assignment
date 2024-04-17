package com.example.acharyaprashant.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acharyaprashant.model.unsplash.UnsplashPhoto
import com.example.acharyaprashant.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _photos = MutableStateFlow<List<UnsplashPhoto>>(emptyList())
    val photos: StateFlow<List<UnsplashPhoto>> get() = _photos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private var pageSize = 20 // Number of photos to load per page
    private var currentPage = 1 // Current page

    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        if (_loading.value) return // Prevent multiple concurrent requests
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    ApiService.create().getPhotos(page = currentPage, perPage = pageSize)
                _photos.value += response // Append new photos to existing list
                currentPage++
                pageSize++ // Increase page size dynamically

            } catch (e: Exception) {
                // Handle error
                Log.e(TAG, "Error fetching photos", e)
                // Show a toast or update a state indicating the error
            } finally {
                _loading.value = false
            }
        }
    }

}
