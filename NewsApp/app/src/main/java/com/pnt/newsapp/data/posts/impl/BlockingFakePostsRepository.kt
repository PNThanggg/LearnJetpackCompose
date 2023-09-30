package com.pnt.newsapp.data.posts.impl

import com.pnt.newsapp.data.model.BaseResult
import com.pnt.newsapp.data.model.Post
import com.pnt.newsapp.data.model.PostsFeed
import com.pnt.newsapp.data.posts.PostsRepository
import com.pnt.newsapp.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

/**
 * Implementation of com.pnt.newsapp.data.posts.PostsRepository that returns a hardcoded list of
 * com.pnt.newsapp.data.posts.impl.getPosts with resources synchronously.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class BlockingFakePostsRepository : PostsRepository {

    // for now, keep the favorites in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    private val postsFeed = MutableStateFlow<PostsFeed?>(null)

        override suspend fun getPost(postId: String?): BaseResult<Post> {
        return withContext(Dispatchers.IO) {
            val post = posts.allPosts.find { it.id == postId }
            if (post == null) {
                BaseResult.Error(IllegalArgumentException("Unable to find post"))
            } else {
                BaseResult.Success(post)
            }
        }
    }

    override suspend fun getPostsFeed(): BaseResult<PostsFeed> {
        postsFeed.update { posts }
        return BaseResult.Success(posts)
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites
    override fun observePostsFeed(): Flow<PostsFeed?> = postsFeed

    override suspend fun toggleFavorite(postId: String) {
        favorites.update { it.addOrRemove(postId) }
    }
}
