package com.pnt.caster.data

import com.pnt.caster.data.model.Episode
import com.pnt.caster.data.room.EpisodesDao
import kotlinx.coroutines.flow.Flow

/**
 * A data repository for [Episode] instances.
 */
class EpisodeStore(
    private val episodesDao: EpisodesDao
) {
    /**
     * Returns a flow containing the episode given [episodeUri].
     */
    fun episodeWithUri(episodeUri: String): Flow<Episode> {
        return episodesDao.episode(episodeUri)
    }

    /**
     * Returns a flow containing the list of episodes associated with the podcast with the
     * given [podcastUri].
     */
    fun episodesInPodcast(
        podcastUri: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Episode>> {
        return episodesDao.episodesForPodcastUri(podcastUri, limit)
    }

    /**
     * Add a new [Episode] to this store.
     *
     * This automatically switches to the main thread to maintain thread consistency.
     */
    suspend fun addEpisodes(episodes: Collection<Episode>) = episodesDao.insertAll(episodes)

    suspend fun isEmpty(): Boolean = episodesDao.count() == 0
}
