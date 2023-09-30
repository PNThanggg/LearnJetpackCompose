package com.pnt.newsapp

import android.app.Application
import com.pnt.newsapp.data.AppContainer
import com.pnt.newsapp.data.AppContainerImpl

class NewsApplication : Application() {
    companion object {
        const val NEWS_APP_URI = "https://developer.android.com/jetnews"
    }

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

}
