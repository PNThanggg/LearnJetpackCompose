package com.pnt.nid.core.data.model

import com.pnt.nid.core.database.model.TopicEntity
import com.pnt.nid.core.network.model.NetworkTopic


fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl,
)