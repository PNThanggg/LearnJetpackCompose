package com.pnt.nid.core.data.di

import com.pnt.nid.core.data.repository.DefaultRecentSearchRepository
import com.pnt.nid.core.data.repository.DefaultSearchContentsRepository
import com.pnt.nid.core.data.repository.NewsRepository
import com.pnt.nid.core.data.repository.OfflineFirstNewsRepository
import com.pnt.nid.core.data.repository.OfflineFirstTopicsRepository
import com.pnt.nid.core.data.repository.OfflineFirstUserDataRepository
import com.pnt.nid.core.data.repository.RecentSearchRepository
import com.pnt.nid.core.data.repository.SearchContentsRepository
import com.pnt.nid.core.data.repository.TopicsRepository
import com.pnt.nid.core.data.repository.UserDataRepository
import com.pnt.nid.core.data.util.ConnectivityManagerNetworkMonitor
import com.pnt.nid.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTopicRepository(
        topicsRepository: OfflineFirstTopicsRepository,
    ): TopicsRepository

    @Binds
    fun bindsNewsResourceRepository(
        newsRepository: OfflineFirstNewsRepository,
    ): NewsRepository

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: DefaultRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentsRepository(
        searchContentsRepository: DefaultSearchContentsRepository,
    ): SearchContentsRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
