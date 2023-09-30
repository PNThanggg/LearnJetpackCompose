package com.pnt.nid.core.network

import com.pnt.nid.core.network.model.NetworkChangeList
import com.pnt.nid.core.network.model.NetworkNewsResource
import com.pnt.nid.core.network.model.NetworkTopic

interface NiaNetworkDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}