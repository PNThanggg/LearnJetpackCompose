package com.pnt.nid.core.data.repository

import androidx.annotation.VisibleForTesting
import com.pnt.nid.core.model.DarkThemeConfig
import com.pnt.nid.core.model.ThemeBrand
import com.pnt.nid.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
//    private val niaPreferencesDataSource: NiaPreferencesDataSource,
//    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    //    override val userData: Flow<UserData> =
//        niaPreferencesDataSource.userData
//
//    @VisibleForTesting
//    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
//        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)
//
//    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) {
//        niaPreferencesDataSource.setTopicIdFollowed(followedTopicId, followed)
////        analyticsHelper.logTopicFollowToggled(followedTopicId, followed)
//    }
//
//    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) {
//        niaPreferencesDataSource.setNewsResourceBookmarked(newsResourceId, bookmarked)
//
////        analyticsHelper.logNewsResourceBookmarkToggled(
////            newsResourceId = newsResourceId,
////            isBookmarked = bookmarked,
////        )
//    }
//
//    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) =
//        niaPreferencesDataSource.setNewsResourceViewed(newsResourceId, viewed)
//
//    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
//        niaPreferencesDataSource.setThemeBrand(themeBrand)
//
////        analyticsHelper.logThemeChanged(themeBrand.name)
//    }
//
//    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
//        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
//
////        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
//    }
//
//    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
//        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
//
////        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
//    }
//
//    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
//        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
//
////        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
//    }
    override val userData: Flow<UserData>
        get() = TODO("Not yet implemented")

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        TODO("Not yet implemented")
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        TODO("Not yet implemented")
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        TODO("Not yet implemented")
    }
}
