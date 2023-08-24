package com.pnt.caster.presentation.home.category

import com.pnt.caster.Graph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnt.caster.data.CategoryStore
import com.pnt.caster.data.PodcastStore
import com.pnt.caster.data.PodcastWithExtraInfo
import com.pnt.caster.data.model.EpisodeToPodcast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PodcastCategoryViewModel(
    private val categoryId: Long,
    private val categoryStore: CategoryStore = Graph.categoryStore,
    private val podcastStore: PodcastStore = Graph.podcastStore
) : ViewModel() {
    private val _state = MutableStateFlow(PodcastCategoryViewState())

    val state: StateFlow<PodcastCategoryViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val recentPodcastsFlow = categoryStore.podcastsInCategorySortedByPodcastCount(
                categoryId,
                limit = 10
            )

            val episodesFlow = categoryStore.episodesFromPodcastsInCategory(
                categoryId,
                limit = 20
            )

            // Combine our flows and collect them into the view state StateFlow
            combine(recentPodcastsFlow, episodesFlow) { topPodcasts, episodes ->
                PodcastCategoryViewState(
                    topPodcasts = topPodcasts,
                    episodes = episodes
                )
            }.collect { _state.value = it }
        }
    }

    fun onTogglePodcastFollowed(podcastUri: String) {
        viewModelScope.launch {
            podcastStore.togglePodcastFollowed(podcastUri)
        }
    }
}

data class PodcastCategoryViewState(
    val topPodcasts: List<PodcastWithExtraInfo> = emptyList(),
    val episodes: List<EpisodeToPodcast> = emptyList()
)
