//package com.pnt.nid.sync.services
//
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//private const val SYNC_TOPIC_SENDER = "/topics/sync"
//
//@AndroidEntryPoint
//class SyncNotificationsService : FirebaseMessagingService() {
//
//    @Inject
//    lateinit var syncManager: SyncManager
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        if (SYNC_TOPIC_SENDER == message.from) {
//            syncManager.requestSync()
//        }
//    }
//}
