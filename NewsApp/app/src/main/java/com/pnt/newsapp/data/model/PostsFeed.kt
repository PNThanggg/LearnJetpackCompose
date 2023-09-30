package com.pnt.newsapp.data.model

/**
 * A container of [Post]s, partitioned into different categories.
 */
data class PostsFeed(
    val highlightedPost: Post,
    val recommendedPosts: List<Post>,
    val popularPosts: List<Post>,
    val recentPosts: List<Post>,
) {
    /**
     * Returns a flattened list of all com.pnt.newsapp.data.posts.impl.getPosts contained in the feed.
     */
    val allPosts: List<Post> =
        listOf(highlightedPost) + recommendedPosts + popularPosts + recentPosts
}
