package com.pnt.caster.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.pnt.caster.data.model.Episode
import com.pnt.caster.data.model.EpisodeToPodcast
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Episode] related operations.
 */
@Dao
abstract class EpisodesDao : BaseDao<Episode> {

    @Query(
        """
        SELECT * FROM episodes WHERE uri = :uri
        """
    )
    abstract fun episode(uri: String): Flow<Episode>

    @Query(
        """
        SELECT * FROM episodes WHERE podcast_uri = :podcastUri
        ORDER BY datetime(published) DESC
        LIMIT :limit
        """
    )
    abstract fun episodesForPodcastUri(
        podcastUri: String,
        limit: Int
    ): Flow<List<Episode>>

    @Transaction
    @Query(
        """
        SELECT episodes.* FROM episodes
        INNER JOIN podcast_category_entries ON episodes.podcast_uri = podcast_category_entries.podcast_uri
        WHERE category_id = :categoryId
        ORDER BY datetime(published) DESC
        LIMIT :limit
        """
    )
    abstract fun episodesFromPodcastsInCategory(
        categoryId: Long,
        limit: Int
    ): Flow<List<EpisodeToPodcast>>

    @Query("SELECT COUNT(*) FROM episodes")
    abstract suspend fun count(): Int
}
