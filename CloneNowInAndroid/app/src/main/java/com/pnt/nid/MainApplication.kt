package com.pnt.nid

import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.pnt.nid.sync.initializers.Sync
import javax.inject.Inject
import javax.inject.Provider

class MainApplication : MultiDexApplication(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        // Initialize com.pnt.nid.sync.initializers.Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}