package com.pnt.newsapp.data

import android.content.Context
import com.pnt.newsapp.data.interests.InterestsRepository
import com.pnt.newsapp.data.interests.impl.FakeInterestsRepository
import com.pnt.newsapp.data.posts.PostsRepository
import com.pnt.newsapp.data.posts.impl.FakePostsRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val postsRepository: PostsRepository
    val interestsRepository: InterestsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val postsRepository: PostsRepository by lazy {
        FakePostsRepository()
    }

    override val interestsRepository: InterestsRepository by lazy {
        FakeInterestsRepository()
    }
}
