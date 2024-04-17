package com.example.acharyaprashant
import android.content.Context
import android.graphics.Bitmap
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.CoilUtils
import coil.util.CoilUtils.createDefaultCache
import okhttp3.OkHttpClient
import java.io.File


object ImageCache {
    lateinit var imageLoader: ImageLoader

    fun initialize(context: Context) {
        val memoryCache = MemoryCache.Builder(context)
            .maxSizeBytes(50 * 1024 * 1024) // 50MB
            .build()

        val diskCache = DiskCache.Builder()
            .directory(File(context.cacheDir, "image_cache"))
            .maxSizeBytes(100 * 1024 * 1024) // 100MB
            .build()

        imageLoader = ImageLoader.Builder(context)
            .memoryCache(memoryCache)
            .diskCache(diskCache)
            .build()
    }
}



/*
object ImageCache {
    lateinit var imageLoader: ImageLoader

    fun initialize(context: Context) {
        imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .directory(File(context.cacheDir, "image_cache"))
                    .maxSizeBytes(100 * 1024 * 1024) // 100MB
                    .build()
            }
            .build()
    }
}
*/
