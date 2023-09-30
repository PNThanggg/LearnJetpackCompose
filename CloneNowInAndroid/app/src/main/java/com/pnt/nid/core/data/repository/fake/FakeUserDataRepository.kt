package com.pnt.nid.core.data.repository.fake

import com.pnt.nid.core.data.repository.UserDataRepository
import com.pnt.nid.core.model.DarkThemeConfig
import com.pnt.nid.core.model.ThemeBrand
import com.pnt.nid.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake implementation of the [UserDataRepository] that returns hardcoded user data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserDataRepository @Inject constructor(
//    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : UserDataRepository {

    //    override val userData: Flow<UserData> =
//        niaPreferencesDataSource.userData
//
//    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
//        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)
//
//    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) =
//        niaPreferencesDataSource.setTopicIdFollowed(followedTopicId, followed)
//
//    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) {
//        niaPreferencesDataSource.setNewsResourceBookmarked(newsResourceId, bookmarked)
//    }
//
//    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) =
//        niaPreferencesDataSource.setNewsResourceViewed(newsResourceId, viewed)
//
//    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
//        niaPreferencesDataSource.setThemeBrand(themeBrand)
//    }
//
//    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
//        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
//    }
//
//    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
//        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
//    }
//
//    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
//        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
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
