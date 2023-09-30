package com.pnt.nid.core.notifications

import com.pnt.nid.core.model.NewsResource

/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun postNewsNotifications(newsResources: List<NewsResource>)
}
