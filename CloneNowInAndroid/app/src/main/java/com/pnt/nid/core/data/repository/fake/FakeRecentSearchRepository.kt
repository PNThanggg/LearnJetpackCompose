package com.pnt.nid.core.data.repository.fake

import com.pnt.nid.core.data.model.RecentSearchQuery
import com.pnt.nid.core.data.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Fake implementation of the [RecentSearchRepository]
 */
class FakeRecentSearchRepository @Inject constructor() : RecentSearchRepository {
    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) { /* no-op */ }

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        flowOf(emptyList())

    override suspend fun clearRecentSearches() { /* no-op */ }
}
