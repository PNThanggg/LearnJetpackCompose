package com.pnt.caster.data.room

import androidx.room.Dao
import androidx.room.Query
import com.pnt.caster.data.model.PodcastFollowedEntry

@Dao
abstract class PodcastFollowedEntryDao : BaseDao<PodcastFollowedEntry> {
    @Query("DELETE FROM podcast_followed_entries WHERE podcast_uri = :podcastUri")
    abstract suspend fun deleteWithPodcastUri(podcastUri: String)

    @Query("SELECT COUNT(*) FROM podcast_followed_entries WHERE podcast_uri = :podcastUri")
    protected abstract suspend fun podcastFollowRowCount(podcastUri: String): Int

    suspend fun isPodcastFollowed(podcastUri: String): Boolean {
        return podcastFollowRowCount(podcastUri) > 0
    }
}
