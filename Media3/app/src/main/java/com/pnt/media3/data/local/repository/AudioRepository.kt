package com.pnt.media3.data.local.repository

import com.pnt.media3.data.local.ContentResolverHelper
import com.pnt.media3.data.local.model.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper
) {
    suspend fun getAudioData(): List<Audio> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }
}