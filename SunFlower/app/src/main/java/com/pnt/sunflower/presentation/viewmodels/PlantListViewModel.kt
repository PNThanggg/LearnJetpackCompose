package com.pnt.sunflower.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pnt.sunflower.data.local.model.Plant
import com.pnt.sunflower.data.local.repository.PlantRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * The ViewModel for plant list.
 */
class PlantListViewModel(
    plantRepository: PlantRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle[GROW_ZONE_SAVED_STATE_KEY] ?: NO_GROW_ZONE
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val plants: LiveData<List<Plant>> = growZone.flatMapLatest { zone ->
        if (zone == NO_GROW_ZONE) {
            plantRepository.getPlants()
        } else {
            plantRepository.getPlantsWithGrowZoneNumber(zone)
        }
    }.asLiveData()

    init {

        /**
         * When `growZone` changes, store the new value in `savedStateHandle`.
         *
         * There are a few ways to write this; all of these are equivalent. (This info is from
         * https://github.com/android/sunflower/pull/671#pullrequestreview-548900174)
         *
         * 1) A verbose version:
         *
         *    viewModelScope.launch {
         *        growZone.onEach { newGrowZone ->
         *            savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
         *        }
         *    }.collect()
         *
         * 2) A simpler version of 1). Since we're calling `collect`, we can consume
         *    the elements in the `collect`'s lambda block instead of using the `onEach` operator.
         *    This is the version that's used in the live code below.
         *
         * 3) We can avoid creating a new coroutine using the `launchIn` terminal operator. In this
         *    case, `onEach` is needed because `launchIn` doesn't take a lambda to consume the new
         *    element in the Flow; it takes a `CoroutineScope` that's used to create a coroutine
         *    internally.
         *
         *    growZone.onEach { newGrowZone ->
         *        savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
         *    }.launchIn(viewModelScope)
         */
        viewModelScope.launch {
            growZone.collect { newGrowZone ->
                savedStateHandle[GROW_ZONE_SAVED_STATE_KEY] = newGrowZone
            }
        }
    }

    fun updateData() {
        if (isFiltered()) {
            clearGrowZoneNumber()
        } else {
            setGrowZoneNumber(9)
        }
    }

    fun setGrowZoneNumber(num: Int) {
        growZone.value = num
    }

    fun clearGrowZoneNumber() {
        growZone.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZone.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }
}