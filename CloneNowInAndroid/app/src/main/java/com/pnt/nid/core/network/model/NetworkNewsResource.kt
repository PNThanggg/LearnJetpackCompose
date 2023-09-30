package com.pnt.nid.core.network.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class NetworkNewsResource(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    val publishDate: Instant,
    val type: String,
    val topics: List<String> = listOf(),
)

@Serializable
data class NetworkNewsResourceExpanded(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    val publishDate: Instant,
    val type: String,
    val topics: List<NetworkTopic> = listOf(),
)