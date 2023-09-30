package com.pnt.nid.core.data.repository.fake

import com.pnt.nid.core.commom.network.Dispatcher
import com.pnt.nid.core.commom.network.NiaDispatchers
import com.pnt.nid.core.data.Synchronizer
import com.pnt.nid.core.data.repository.TopicsRepository
import com.pnt.nid.core.model.Topic
import com.pnt.nid.core.network.fake.FakeNiaNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Fake implementation of the [TopicsRepository] that retrieves the topics from a JSON String, and
 * uses a local DataStore instance to save and retrieve followed topic ids.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeTopicsRepository @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FakeNiaNetworkDataSource,
) : TopicsRepository {
    override fun getTopics(): Flow<List<Topic>> = flow {
        emit(
            datasource.getTopics().map {
                Topic(
                    id = it.id,
                    name = it.name,
                    shortDescription = it.shortDescription,
                    longDescription = it.longDescription,
                    url = it.url,
                    imageUrl = it.imageUrl,
                )
            },
        )
    }.flowOn(ioDispatcher)

    override fun getTopic(id: String): Flow<Topic> {
        return getTopics().map { it.first { topic -> topic.id == id } }
    }

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
