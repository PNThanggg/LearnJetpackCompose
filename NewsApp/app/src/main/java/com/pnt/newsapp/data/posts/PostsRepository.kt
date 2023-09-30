package com.pnt.newsapp.data.posts

import com.pnt.newsapp.data.model.BaseResult
import com.pnt.newsapp.data.model.Post
import com.pnt.newsapp.data.model.PostsFeed
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the Posts data layer.
 */
interface PostsRepository {

    /**
     * Get a specific JetNews post.
     */
    suspend fun getPost(postId: String?): BaseResult<Post>

    /**
     * Get JetNews com.pnt.newsapp.data.posts.impl.getPosts.
     */
    suspend fun getPostsFeed(): BaseResult<PostsFeed>

    /**
     * Observe the current favorites
     */
    fun observeFavorites(): Flow<Set<String>>

    /**
     * Observe the com.pnt.newsapp.data.posts.impl.getPosts feed.
     */
    fun observePostsFeed(): Flow<PostsFeed?>

    /**
     * Toggle a postId to be a favorite or not.
     */
    suspend fun toggleFavorite(postId: String)
}
