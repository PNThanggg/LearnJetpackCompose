package com.pnt.caster

import android.content.Context
import androidx.room.Room
import com.pnt.caster.data.CategoryStore
import com.pnt.caster.data.EpisodeStore
import com.pnt.caster.data.PodcastStore
import com.pnt.caster.data.PodcastsFetcher
import com.pnt.caster.data.PodcastsRepository
import com.pnt.caster.data.room.CasterDatabase
import com.pnt.caster.data.room.TransactionRunner
import com.rometools.rome.io.SyndFeedInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.LoggingEventListener
import java.io.File

/**
 * A very simple global singleton dependency graph.
 *
 * For a real app, you would use something like Hilt/Dagger instead.
 */
object Graph {
    private lateinit var okHttpClient: OkHttpClient

    lateinit var database: CasterDatabase
        private set

    private val transactionRunner: TransactionRunner
        get() = database.transactionRunnerDao()

    private val syndFeedInput by lazy { SyndFeedInput() }

    val podcastRepository by lazy {
        PodcastsRepository(
            podcastsFetcher = podcastFetcher,
            podcastStore = podcastStore,
            episodeStore = episodeStore,
            categoryStore = categoryStore,
            transactionRunner = transactionRunner,
            mainDispatcher = mainDispatcher
        )
    }

    private val podcastFetcher by lazy {
        PodcastsFetcher(
            okHttpClient = okHttpClient,
            syndFeedInput = syndFeedInput,
            ioDispatcher = ioDispatcher
        )
    }

    val podcastStore by lazy {
        PodcastStore(
            podcastDao = database.podcastsDao(),
            podcastFollowedEntryDao = database.podcastFollowedEntryDao(),
            transactionRunner = transactionRunner
        )
    }

    val episodeStore by lazy {
        EpisodeStore(
            episodesDao = database.episodesDao()
        )
    }

    val categoryStore by lazy {
        CategoryStore(
            categoriesDao = database.categoriesDao(),
            categoryEntryDao = database.podcastCategoryEntryDao(),
            episodesDao = database.episodesDao(),
            podcastsDao = database.podcastsDao()
        )
    }

    private val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    private val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    fun provide(context: Context) {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), (20 * 1024 * 1024).toLong()))
            .apply {
//                if (BuildConfig.DEBUG) eventListenerFactory(LoggingEventListener.Factory())
                eventListenerFactory(LoggingEventListener.Factory())
            }
            .build()

        database = Room.databaseBuilder(context, CasterDatabase::class.java, "data.db")
            // This is not recommended for normal apps, but the goal of this sample isn't to
            // showcase all of Room.
            .fallbackToDestructiveMigration()
            .build()
    }
}
